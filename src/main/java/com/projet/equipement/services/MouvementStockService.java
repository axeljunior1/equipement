package com.projet.equipement.services;


import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.MouvementStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MouvementStockService {
    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    public List<MouvementStock> findAll(){
        return mouvementStockRepository.findAll();
    }
    public  MouvementStock findById(Long id){
        return  mouvementStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mouvement de stock", id));

    }
    public MouvementStock save(MouvementStock mouvementsStock){
        return mouvementStockRepository.save(mouvementsStock);
    }
    public void deleteById(Long id){
        mouvementStockRepository.deleteById(id);
    }



}
