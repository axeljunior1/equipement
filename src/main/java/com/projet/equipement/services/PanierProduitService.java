package com.projet.equipement.services;


import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.panierProduit.PanierProduitPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitUpdateDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.PanierProduitMapper;
import com.projet.equipement.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PanierProduitService {
    private final PanierProduitRepository panierProduitRepository;
    private final PanierProduitMapper panierProduitMapper;
    private final EntityManager entityManager;
    private final UniteVenteService uniteVenteService;
    private final FormatVenteService formatVenteService;
    private final FormatVenteRepository formatVenteRepository;
    private final UniteVenteRepository uniteVenteRepository;
    private final ProduitRepository produitRepository;

    public PanierProduitService(PanierProduitRepository panierProduitRepository, PanierProduitMapper panierProduitMapper, EntityManager entityManager, UniteVenteService uniteVenteService, FormatVenteService formatVenteService, FormatVenteRepository formatVenteRepository, UniteVenteRepository uniteVenteRepository, ProduitRepository produitRepository) {
        this.panierProduitRepository = panierProduitRepository;
        this.panierProduitMapper = panierProduitMapper;
        this.entityManager = entityManager;
        this.uniteVenteService = uniteVenteService;
        this.formatVenteService = formatVenteService;
        this.formatVenteRepository = formatVenteRepository;
        this.uniteVenteRepository = uniteVenteRepository;
        this.produitRepository = produitRepository;
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
            FormatVente formatVente = formatVenteService.findAllByProduitId(panierProduit.getProduitId(), Pageable.unpaged()).getContent().stream().findFirst().orElseThrow(()-> new EntityNotFoundException("Format de vente", panierProduit.getProduitId().toString()));

            PanierProduit entity = panierProduitMapper.toEntity(panierProduit);
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
