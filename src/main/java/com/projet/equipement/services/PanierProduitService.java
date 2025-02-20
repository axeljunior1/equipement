package com.projet.equipement.services;


import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.panierProduit.PanierProduitPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitUpdateDto;
import com.projet.equipement.entity.Panier;
import com.projet.equipement.entity.PanierProduit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.PanierProduitMapper;
import com.projet.equipement.repository.PanierProduitRepository;
import com.projet.equipement.repository.PanierRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PanierProduitService {
    private final PanierProduitRepository panierProduitRepository;
    private final PanierProduitMapper panierProduitMapper;
    private final EntityManager entityManager;

    public PanierProduitService(PanierProduitRepository panierProduitRepository, PanierProduitMapper panierProduitMapper, EntityManager entityManager) {
        this.panierProduitRepository = panierProduitRepository;
        this.panierProduitMapper = panierProduitMapper;
        this.entityManager = entityManager;
    }

    public Page<PanierProduitGetDto> findAll(Pageable pageable) {
        return panierProduitRepository.findAll(pageable).map(panierProduitMapper::toGetDto);
    }

    public List<PanierProduitGetDto> findAllByPanierId(Long id) {
        return (panierProduitRepository.findAllByPanierId(id)).stream().map(panierProduitMapper::toGetDto).collect(Collectors.toList());
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
            saved = panierProduitRepository.save(produit);
        }else{
             saved = panierProduitRepository.save(panierProduitMapper.toEntity(panierProduit));
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
