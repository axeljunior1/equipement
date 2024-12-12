package com.projet.equipement.services;


import com.projet.equipement.entity.Sortie;
import com.projet.equipement.repository.SortieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SortieService {
    @Autowired
    private SortieRepository sortieRepository;

    public List<Sortie> findAll() {
        return sortieRepository.findAll();
    }

    public Optional<Sortie> findById(int id) {
        return Optional.of(sortieRepository.findById(id)).get();
    }

    public Sortie save(Sortie Sortie) {
        return sortieRepository.save(Sortie);
    }

    public void deleteById(int id) {
        sortieRepository.deleteById(id);
    }


}
