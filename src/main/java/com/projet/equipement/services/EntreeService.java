package com.projet.equipement.services;


import com.projet.equipement.dto.entree.EntreePostDto;
import com.projet.equipement.dto.entree.EntreeUpdateDto;
import com.projet.equipement.entity.Entree;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.EntreeMapper;
import com.projet.equipement.repository.EntreeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntreeService {

    private final EntreeRepository entreeRepository;
    private final EntreeMapper entreeMapper;
    private final ProduitService produitService;
    private final FournisseurService fournisseurService;
    private final UtilisateurService utilisateurService;

    public EntreeService(EntreeRepository entreeRepository, EntreeMapper entreeMapper, ProduitService produitService, FournisseurService fournisseurService, UtilisateurService utilisateurService) {
        this.entreeRepository = entreeRepository;
        this.entreeMapper = entreeMapper;
        this.produitService = produitService;
        this.fournisseurService = fournisseurService;
        this.utilisateurService = utilisateurService;
    }

    public List<Entree> findAll(){
        return entreeRepository.findAll();
    }
    public  Entree findById(Long id){
        return  entreeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entree", id));
    }
    public Entree createEntree(EntreePostDto entreePostDto){

        // Save l'entree
        Entree entreeSave = entreeRepository.save(entreeMapper.createEntreeFromDto(entreePostDto,  produitService,  fournisseurService, utilisateurService));



        return entreeSave;
    }

    public Entree updateEntree(Long id, EntreeUpdateDto entreeUpdateDto){
        Entree entree = findById(id);
        entreeMapper.updateEntreeFromDto(entreeUpdateDto, entree, produitService,  fournisseurService, utilisateurService);

//        MouvementStock mouvementStock = mouvementStockService.findById(entree.getM)

        return entreeRepository.save(entree);
    }
    public void deleteById(Long id){
        entreeRepository.deleteById(id);
    }



}
