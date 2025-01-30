package com.projet.equipement.services;


import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class LigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final LigneVenteMapper ligneVenteMapper;
    private final VenteService venteService;
    private final ProduitService produitService;
    private final MouvementStockService mouvementStockService;

    public LigneVenteService(LigneVenteRepository ligneVenteRepository, LigneVenteMapper ligneVenteMapper, VenteService venteService, ProduitService produitService, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, ProduitRepository produitRepository, TypeMouvementStockRepository typeMouvementStockRepository, MouvementStockService mouvementStockService) {
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneVenteMapper = ligneVenteMapper;
        this.venteService = venteService;
        this.produitService = produitService;
        this.mouvementStockService = mouvementStockService;
    }

    public Page<LigneVente> findAll(Pageable pageable){
        return ligneVenteRepository.findAll(pageable);
    }

    public  LigneVente findById(Long id){
        return  ligneVenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneVente", id));
    }

    @Transactional
    public LigneVente save(LigneVentePostDto ligneVentePostDto){

        LigneVente ligneVente = ligneVenteMapper.postLigneVenteFromDto(ligneVentePostDto, venteService, produitService);
        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVente);
        // Gestion du mvt de stock
        mouvementStockService.enregistrerMouvementStock(
                Long.valueOf(ligneVentePostDto.getProduitId()),
                ligneVentePostDto.getQuantite(),
                "VTE_"+ ligneVentePostDto.getVenteId() + "_LIG_"+ saveLigneVente.getId(),
                "Généré à partir de la ligne d'une vente",
                "VENTE_PRODUIT",
                Math.toIntExact(ligneVente.getVente().getId()),
                Math.toIntExact(ligneVente.getId())
        );
        return saveLigneVente;
    }


    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id){
        LigneVente ligneVente = findById(id);
        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente, venteService, produitService);
        return ligneVenteRepository.save(ligneVente);
    }


    @Transactional
    public void deleteById(Long id){
        // cet id est l'id de la ligne d'achat
        LigneVente ligneVente = findById(id);
        String reference = "VTE_"+ ligneVente.getVente().getId() + "_LIG_"+ ligneVente.getId() ;;
        mouvementStockService.deleteByReference(reference);
        ligneVenteRepository.deleteById(id);
    }

}
