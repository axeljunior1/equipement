package com.projet.equipement.services;


import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.panierProduit.PanierProduitPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitUpdateDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.PanierProduitMapper;
import com.projet.equipement.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PanierProduitService {
    private final PanierProduitRepository panierProduitRepository;
    private final PanierProduitMapper panierProduitMapper;
    private final FormatVenteService formatVenteService;
    private final FormatVenteRepository formatVenteRepository;
    private final UniteVenteRepository uniteVenteRepository;
    private final ProduitRepository produitRepository;
    private final PanierRepository panierRepository;

    public PanierProduitService(PanierProduitRepository panierProduitRepository,
                                PanierProduitMapper panierProduitMapper,
                                FormatVenteService formatVenteService,
                                FormatVenteRepository formatVenteRepository,
                                UniteVenteRepository uniteVenteRepository,
                                ProduitRepository produitRepository,
                                PanierRepository panierRepository) {
        this.panierProduitRepository = panierProduitRepository;
        this.panierProduitMapper = panierProduitMapper;
        this.formatVenteService = formatVenteService;
        this.formatVenteRepository = formatVenteRepository;
        this.uniteVenteRepository = uniteVenteRepository;
        this.produitRepository = produitRepository;
        this.panierRepository = panierRepository;
    }

    public Page<PanierProduitGetDto> findAll(Pageable pageable) {
        return panierProduitRepository.findAll(pageable).map(panierProduitMapper::toGetDto);
    }

    @Transactional
    public List<PanierProduitGetDto> findAllByPanierId(Long id) {
        List<PanierProduitGetDto> panierProduitGetDtos = (panierProduitRepository.findAllByPanierId(id)).stream().map(panierProduitMapper::toGetDto).toList();
        for (PanierProduitGetDto p : panierProduitGetDtos) {

            Produit produit = produitRepository.findById(p.getId()).orElseThrow(()-> new EntityNotFoundException("Produit", p.getId()));

            initFormatVente(produit);

        }

        return panierProduitGetDtos.stream().map(p -> {

            ProduitGetDto produitGetDto= p.getProduit();
            List<FormatVente> formatVentes = formatVenteService.findAllByProduitId(produitGetDto.getId(), Pageable.unpaged()).getContent();

            produitGetDto.setFormatVentes(formatVentes);

            return p ;
        }).toList();
    }

    private void initFormatVente(Produit produit) {
        Page<FormatVente> formatVentes = formatVenteRepository.findByProduitId(produit.getId(), Pageable.unpaged());

        if (formatVentes.isEmpty()){
            FormatVente formatVentePost = new FormatVente();
            formatVentePost.setPrixVente(BigDecimal.valueOf(produit.getPrixVente()));
            formatVentePost.setQuantiteParFormat(1);
            formatVentePost.setLibelleFormat("Produit à l'unité");
            formatVentePost.setProduit(produit);
            formatVentePost.setUniteVente(uniteVenteRepository.findByCode("UNI").orElseThrow(()-> new EntityNotFoundException("Unité de vente", "UNI")));
            formatVentePost.setTenantId(TenantContext.getTenantId());
            formatVenteRepository.save(formatVentePost);
        }
    }

    public PanierProduitGetDto findById(Long id) {
        return panierProduitMapper.toGetDto(panierProduitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PanierProduit", id)));
    }
    public PanierProduitGetDto findByIdPanierAndIdProduit(Long idPanier, Long idProduit) {
        return panierProduitMapper.toGetDto(panierProduitRepository.findByIdPanierAndIdProduit(idPanier, idProduit)
                .orElseThrow(() -> new EntityNotFoundException("PanierProduit", "idPanier" + idPanier + " idProduit" + idProduit )));
    }


    public PanierProduitGetDto save(PanierProduitPostDto panierProduit) {

        PanierProduit saved = null;

        PanierProduit produit = panierProduitRepository.findByIdPanierAndIdProduit(
                panierProduit.getPanierId(),
                panierProduit.getProduitId()).orElse(null);

        if (produit != null) {
            // cas de modification de la qte dans le panier
            produit.setQuantite(panierProduit.getQuantite());
            produit.setTenantId(TenantContext.getTenantId());
            produit.setPrixVente(panierProduit.getPrixVente());

            FormatVente formatVente = produit.getFormatVente();
            if (panierProduit.getFormatVenteId() != null) {
                formatVente = formatVenteService.findById(panierProduit.getFormatVenteId());
            }
            produit.setFormatVente(formatVente);
            saved = panierProduitRepository.save(produit);
        }else{
            UniteVente uniteVente = uniteVenteRepository.findByCode("UNI").orElseThrow(()-> new EntityNotFoundException("Unité vente", "UNI"));
            FormatVente formatVente = formatVenteService.findByUniteVente_IdAndProduit_Id(uniteVente.getId(), panierProduit.getProduitId());

            PanierProduit entity = panierProduitMapper.toEntity(panierProduit);
            Panier panier = panierRepository.findById(panierProduit.getPanierId()).orElseThrow(()->new EntityNotFoundException("Panier", panierProduit.getPanierId()));
            entity.setPanier(panier);
            entity.setProduit(produitRepository.findById(panierProduit.getProduitId()).orElseThrow(()->new EntityNotFoundException("Produit", panierProduit.getProduitId())));
            entity.setFormatVente(formatVente);
            entity.setTenantId(TenantContext.getTenantId());
            saved = panierProduitRepository.save(entity);
        }

        return panierProduitMapper.toGetDto(saved);
    }

    public void deleteById(Long id) {
        panierProduitRepository.deleteById(id);
    }

    public PanierProduitGetDto update(PanierProduitUpdateDto updatedPanierProduit, Long id) {
        PanierProduit panierProduit = panierProduitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("PanierProduit", id));

        if (updatedPanierProduit != null) {
            if (updatedPanierProduit.getQuantite() != null) {
                panierProduit.setQuantite(updatedPanierProduit.getQuantite());
            }
            if (updatedPanierProduit.getPrixVente() != null) {
                panierProduit.setPrixVente(updatedPanierProduit.getPrixVente());
            }
        }

        panierProduit.setTenantId(TenantContext.getTenantId());
        PanierProduit saved = panierProduitRepository.save(panierProduit);
        // notifie les client du changement dans le panier
//        cartUpdateService.notifyCartUpdate(saved);
        return panierProduitMapper.toGetDto(saved);
    }


    public Boolean isPresent(Long idPanier, Long idProduit) {
        Optional<PanierProduit> produit = panierProduitRepository.findByIdPanierAndIdProduit(idPanier, idProduit);
        return produit.isPresent();
    }

    public Integer getQte(Long idPanier, Long idProduit) {
        Optional<PanierProduit> produit = panierProduitRepository.findByIdPanierAndIdProduit(idPanier, idProduit);
        return produit.isPresent() ? produit.get().getQuantite() : 0;
    }
}
