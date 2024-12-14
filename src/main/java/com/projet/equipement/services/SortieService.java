package com.projet.equipement.services;


import com.projet.equipement.entity.Sortie;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.SortieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortieService {
    @Autowired
    private SortieRepository sortieRepository;

    public List<Sortie> findAll() {
        return sortieRepository.findAll();
    }

    public  Sortie findById(Long id) {
        return sortieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sortie", id));
    }

    public Sortie save(Sortie Sortie) {
        return sortieRepository.save(Sortie);
    }

    public void deleteById(Long id) {
        sortieRepository.deleteById(id);
    }


}
