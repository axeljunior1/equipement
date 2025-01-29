package com.projet.equipement.services;


import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.TypeMouvementStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final LigneVenteMapper ligneVenteMapper;
    private final VenteService venteService;
    private final ProduitService produitService;
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;
    private final ProduitRepository produitRepository;
    private final TypeMouvementStockRepository typeMouvementStockRepository;

    public LigneVenteService(LigneVenteRepository ligneVenteRepository, LigneVenteMapper ligneVenteMapper, VenteService venteService, ProduitService produitService, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, ProduitRepository produitRepository, TypeMouvementStockRepository typeMouvementStockRepository) {
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneVenteMapper = ligneVenteMapper;
        this.venteService = venteService;
        this.produitService = produitService;
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.produitRepository = produitRepository;
        this.typeMouvementStockRepository = typeMouvementStockRepository;
    }

    public Page<LigneVente> findAll(Pageable pageable){
        return ligneVenteRepository.findAll(pageable);
    }

    public  LigneVente findById(Long id){
        return  ligneVenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneVente", id));
    }

    public LigneVente save(LigneVentePostDto ligneVentePostDto){


        LigneVente ligneVente = ligneVenteMapper.postLigneVenteFromDto(ligneVentePostDto, venteService, produitService);
        LigneVente save = ligneVenteRepository.save(ligneVente);

        // Gestion du mvt de stock
        LocalDateTime dateCreate = LocalDateTime.now();
        MouvementStockPostDto mouvementStockPostDto = MouvementStockPostDto.builder()
                .reference("ACH_"+ ligneVentePostDto.getVenteId() + "_LIG_"+ save.getId() )
                .produitId(Long.valueOf(ligneVentePostDto.getProduitId()))
                .quantite(ligneVentePostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'une vente")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("VENTE_PRODUIT")
                .build();

        // Save un movement de stock
        mouvementStockRepository.save(mouvementStockMapper.PostMouvementStockFromDto(mouvementStockPostDto));

        return save;
    }


    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id){
        LigneVente ligneVente = findById(id);
        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente, venteService, produitService);
        return ligneVenteRepository.save(ligneVente);
    }


    public void deleteById(Long id){
        // cet id est l'id de la ligne d'achat
        LigneVente ligneVente = findById(id);
        Long idProduit = ligneVente.getProduit().getId();
        Produit produit = produitRepository.findById(idProduit).orElseThrow(()-> new EntityNotFoundException("Produit", idProduit));
        ligneVenteRepository.deleteById(id);
        TypeMouvementStock typeMouvementStock = typeMouvementStockRepository.findByCode("ACHAT_MARCHANDISE");
        List<MouvementStock> mouvementStocks = mouvementStockRepository.findByProduitAndTypeMouvement(produit, typeMouvementStock);
        for (MouvementStock mouvementStock : mouvementStocks) {
            mouvementStockRepository.deleteById(mouvementStock.getId());
        }
    }

}
