package com.projet.equipement.services;


import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.StockCourant;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.utils.EAN13Generator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProduitService{

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final StockCourantService stockCourantService;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper, StockCourantService stockCourantService) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.stockCourantService = stockCourantService;
    }

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

    public  ProduitGetDto findProduitDtoById(Long id){
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
        StockCourant stockCourant = stockCourantService.getStockCourantById(produit.getId());

        ProduitGetDto produitGetDto = produitMapper.toGetDto(produit);
        produitGetDto.setStockCourant(stockCourant.getStockCourant());

        return produitGetDto;
    }

    public ProduitGetDto findByEan13(String ean13) {
        return  produitMapper.toGetDto(produitRepository.findByEan13(ean13)
                .orElseThrow(() -> new EntityNotFoundException("Produit", ean13)));
    }

    public List<ProduitGetDto> rechercherProduits(String motCle){
        List<Produit> produitList = produitRepository.rechercherProduits(motCle);
        return produitList.stream().map(produitMapper::toGetDto).collect(Collectors.toList());
    }

    public List<ProduitGetDto> findBySpec(Specification<Produit> spec){
        List<Produit> produitList = produitRepository.findAll(spec);
        return produitList.stream().map(produitMapper::toGetDto).collect(Collectors.toList());
    }

    public ProduitGetDto save(ProduitPostDto produitPostDto){
        Produit produit = produitMapper.toEntity(produitPostDto);
        // Qrcode et code unique ean13
        String EAN_CONST = new EAN13Generator().generateEAN13WithFirstThreeChars("999");
        produit.setEan13(EAN_CONST);
        produit.setQrCode(new EAN13Generator().genAndSaveQrCodeByProduct(EAN_CONST));

        Produit saved = produitRepository.save(produit);
        return produitMapper.toGetDto(saved);
    }

    public Produit save(Produit produit){
        return produitRepository.save(produit);
    }


    public ProduitGetDto updateProduit(ProduitUpdateDto produitUpdateDto, Long id){
        Produit produit = findById(id);
        produitMapper.updateProduitFromDto(produitUpdateDto, produit);
        Produit saved = produitRepository.save(produit);
        return produitMapper.toGetDto(saved);
    }

    /**
     * Soft delete d'un produit ok
     * @param id id du produit
     */
    public void deleteByIdSoft(Long id){
        Produit produit = findById(id);
        produit.setActif(false);
        this.save(produit);
    }


}
