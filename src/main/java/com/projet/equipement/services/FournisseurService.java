package com.projet.equipement.services;


import com.projet.equipement.entity.Fournisseur;
import com.projet.equipement.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {
    @Autowired
    private FournisseurRepository fournisseurRepository;

    public List<Fournisseur> findAll(){
        return fournisseurRepository.findAll();
    }
    public Optional<Fournisseur> findById(int id){
        return Optional.of(fournisseurRepository.findById(id)).get();
    }
    public Fournisseur save(Fournisseur fournisseur){
        return fournisseurRepository.save(fournisseur);
    }
    public void deleteById(int id){
        fournisseurRepository.deleteById(id);
    }

}
