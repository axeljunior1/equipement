package com.projet.equipement.services;


import com.projet.equipement.entity.Entree;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EntreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntreeService {
    @Autowired
    private EntreeRepository entreeRepository;

    public List<Entree> findAll(){
        return entreeRepository.findAll();
    }
    public  Entree findById(Long id){
        return  entreeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entree", id));
    }
    public Entree save(Entree entree){
        return entreeRepository.save(entree);
    }
    public void deleteById(Long id){
        entreeRepository.deleteById(id);
    }



}
