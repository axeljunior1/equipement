package com.projet.equipement.services;


import com.projet.equipement.entity.Categorie;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.CategorieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {
    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Page<Categorie> findAll(Pageable pageable) {
        return categorieRepository.findAll(pageable);
    }
    public Page<Categorie> findByNom(String nom, Pageable pageable) {
        return categorieRepository.findByNom(nom, pageable);
    }

    public Categorie findById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categorie", id));
    }

    public Categorie save(Categorie categorie) {

        return categorieRepository.save(categorie);
    }

    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }





}
