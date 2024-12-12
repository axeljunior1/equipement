package com.projet.equipement.services;


import com.projet.equipement.entity.Entree;
import com.projet.equipement.repository.EntreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntreeService {
    @Autowired
    private EntreeRepository entreeRepository;

    public List<Entree> findAll(){
        return entreeRepository.findAll();
    }
    public Optional<Entree> findById(int id){
        return Optional.of(entreeRepository.findById(id)).get();
    }
    public Entree save(Entree entree){
        return entreeRepository.save(entree);
    }
    public void deleteById(int id){
        entreeRepository.deleteById(id);
    }



}
