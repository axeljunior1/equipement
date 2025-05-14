package com.projet.equipement.services;


import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.StockCourant;
import com.projet.equipement.entity.TarifAchat;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.utils.EAN13Generator;
import com.projet.equipement.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitService{

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final StockCourantService stockCourantService;
    private final EntityManager entityManager;
    private final TarifAchatService tarifAchatService;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper, StockCourantService stockCourantService, EntityManager entityManager, TarifAchatService tarifAchatService) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.stockCourantService = stockCourantService;
        this.entityManager = entityManager;
        this.tarifAchatService = tarifAchatService;
    }

    /**
     * Retrieves a paginated list of active or inactive products and their current stock levels.
     *
     * @param active indicates whether to fetch active products (true) or inactive products (false)
     * @param pageable the pagination information
     * @return a paginated list of products with their respective stock levels
     */
    public Page<ProduitGetDto> findByActif(boolean active, Pageable pageable){
        Page<ProduitGetDto> produitGetDtos = produitRepository.findByActif(active, pageable).map(produitMapper::toGetDto);
        // 2. Récupérer tous les IDs des produits paginés
        List<Long> produitIds = produitGetDtos.stream().map(ProduitGetDto::getId).collect(Collectors.toList());

        // 3. Récupérer tous les stocks courants en une seule requête (optimisé)
        List<StockCourant> stockList = stockCourantService.getStockCourantByIds(produitIds);

        // 4. Convertir la liste en une Map (produitId -> stockCourant)
        Map<Long, Integer> stockMap = stockList.stream()
                .collect(Collectors.toMap(StockCourant::getId, StockCourant::getStockCourant));

        return produitGetDtos.map(produit -> {
             produit.setStockCourant(stockMap.getOrDefault(produit.getId(), 0)); // 0 par défaut si le stock est absent
            return produit;
        });
    }


    public Page<Produit> findAll(Pageable pageable){
        return produitRepository.findAll( pageable);
    }


    public  Produit findById(Long id){
        return  produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }

    /**
     * Retrieves the details of a product along with its current stock level based on its ID.
     *
     * @param id the unique identifier of the product to be retrieved
     * @return a DTO representing the product details and its current stock level
     * @throws EntityNotFoundException if no product is found with the provided ID
     */
    public  ProduitGetDto findProduitDtoById(Long id){
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
        StockCourant stockCourant = stockCourantService.getStockCourantById(produit.getId());

        ProduitGetDto produitGetDto = produitMapper.toGetDto(produit);
        produitGetDto.setPrixAchat(tarifAchatService.findByProduitId(produit.getId()).getPrixAchat());
        produitGetDto.setStockCourant(stockCourant.getStockCourant());

        return produitGetDto;
    }
    public List<Produit> findProduitsByTenantId() {
        String tenantId = TenantContext.getTenantId();
        return entityManager.createQuery("from Produit p where p.tenantId = :tenantId", Produit.class)
                .setParameter("tenantId", tenantId)
                .getResultList();
    }


    public ProduitGetDto findByEan13(String ean13) {
        return  produitMapper.toGetDto(produitRepository.findByEan13(ean13)
                .orElseThrow(() -> new EntityNotFoundException("Produit", ean13)));
    }

    public Page<ProduitGetDto> rechercherProduits(String motCle, Pageable pageable) {
        List<ProduitGetDto> produitsDtos = produitRepository.rechercherProduits(motCle).stream()
                .map(produitMapper::toGetDto)
                .peek(elt -> elt.setStockCourant(
                        Optional.ofNullable(stockCourantService.getStockCourantById(elt.getId()))
                                .map(StockCourant::getStockCourant)
                                .orElse(0) // Valeur par défaut si null
                ))
                .collect(Collectors.toList());

        return PaginationUtil.toPage(produitsDtos, pageable);
    }


    public List<ProduitGetDto> findBySpec(Specification<Produit> spec){
        List<Produit> produitList = produitRepository.findAll(spec);
        return produitList.stream().map(produitMapper::toGetDto).collect(Collectors.toList());
    }

    /**
     * Persists a new product and its associated purchasing tariff in the database.
     * If an EAN-13 code is not provided, a unique code is generated.
     * Also generates and associates a QR code for the product.
     *
     * @param produitPostDto the product data transfer object containing the details of the product to be saved
     * @return ProduitGetDto a data transfer object containing the saved product's details
     * @throws EntityNotFoundException if the saved product cannot be found after persisting
     */
    @Transactional
    public ProduitGetDto save(ProduitPostDto produitPostDto){
        Produit produit = produitMapper.toEntity(produitPostDto);
        // Qrcode et code unique ean13
        String EAN_CONST = new EAN13Generator().generateEAN13WithFirstThreeChars("999");
        if (!Objects.equals(produitPostDto.getEan13(), "") && produitPostDto.getEan13() != null){
            EAN_CONST = produitPostDto.getEan13();
        }
        produit.setEan13(EAN_CONST);
        produit.setQrCode(new EAN13Generator().genAndSaveQrCodeByProduct(EAN_CONST));

        produit.setTenantId(TenantContext.getTenantId());
        Produit p = produitRepository.save(produit);
        Produit saved = produitRepository.findById(p.getId()).orElseThrow(()->new EntityNotFoundException("Produit", p.getId()));
        // Forcer le chargement de la catégorie (si LAZY)


        //Creer un tarif d'achat

        TarifAchatPostDto  tarifAchatPostDto = TarifAchatPostDto.builder()
                .prixAchat(BigDecimal.valueOf(produitPostDto.getPrixAchat()))
                .produitId(saved.getId())
                .build();

        tarifAchatService.save(tarifAchatPostDto);

        entityManager.refresh(saved);

        return produitMapper.toGetDto(saved);
    }

    public Produit save(Produit produit){
        produit.setTenantId(TenantContext.getTenantId());
        return produitRepository.save(produit);
    }

    /**
     * Updates a product with the provided details and returns the updated product as a DTO.
     * Also updates the purchase price in the corresponding purchasing price entity if applicable.
     *
     * @param produitUpdateDto the DTO containing the updated product details
     * @param id the ID of the product to update
     * @return a DTO representing the updated product
     */
    @Transactional
    public ProduitGetDto updateProduit(ProduitUpdateDto produitUpdateDto, Long id){
        Produit produit = findById(id);
        produitMapper.updateProduitFromDto(produitUpdateDto, produit);
        Produit saved = produitRepository.save(produit);

        //Modification tarif achat
        if (produitUpdateDto.getPrixAchat() != null){
            TarifAchat tarifAchat = tarifAchatService.findByProduitId(id);
            tarifAchat.setPrixAchat(BigDecimal.valueOf(produitUpdateDto.getPrixAchat()));
            tarifAchatService.save(tarifAchat);
        }
        return produitMapper.toGetDto(saved);
    }

    /**
     * Soft delete a product by setting its 'actif' flag to false.
     * @param id the ID of the product to update
     */
    public void deleteByIdSoft(Long id){
        Produit produit = findById(id);
        produit.setActif(false);
        this.save(produit);
    }


}
