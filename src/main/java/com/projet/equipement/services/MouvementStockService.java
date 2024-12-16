package com.projet.equipement.services;


import com.projet.equipement.dto.mouvementStock.MouvementStockPostDto;
import com.projet.equipement.dto.mouvementStock.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.MouvementStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MouvementStockService {
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;
    private final ProduitService produitService;
    private final EntreeService entreeService;
    private final UtilisateurService utilisateurService;
    private final SortieService sortieService;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, ProduitService produitService, EntreeService entreeService, UtilisateurService utilisateurService, SortieService sortieService) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.produitService = produitService;
        this.entreeService = entreeService;
        this.utilisateurService = utilisateurService;
        this.sortieService = sortieService;
    }

    public List<MouvementStock> findAll(){
        return mouvementStockRepository.findAll();
    }
    public  MouvementStock findById(Long id){
        return  mouvementStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mouvement de stock", id));

    }
    public MouvementStock save(MouvementStockPostDto mouvementStockPostDto){
        MouvementStock mouvementStock = mouvementStockMapper.createMouvementStockFromDto(mouvementStockPostDto, produitService,  utilisateurService, entreeService, sortieService   );
        return mouvementStockRepository.save(mouvementStock);
    }
    public void deleteById(Long id){
        mouvementStockRepository.deleteById(id);
    }


    public MouvementStock updateMouvementStock(MouvementStockUpdateDto mouvementStockUpdateDto, Long id) {
        MouvementStock mouvementStock = findById(id) ;
         mouvementStockMapper.updateMouvementStockFromDto(mouvementStockUpdateDto, mouvementStock , produitService,  utilisateurService, entreeService, sortieService);
         return mouvementStockRepository.save(mouvementStock);
    }
}
