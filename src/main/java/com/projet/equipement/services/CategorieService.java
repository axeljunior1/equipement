package com.projet.equipement.services;


import com.projet.equipement.dto.categorie.CategoriePostDto;
import com.projet.equipement.dto.categorie.CategorieUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.CategorieMapper;
import com.projet.equipement.repository.CategorieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {
    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    public CategorieService(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
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

    public Categorie save(CategoriePostDto categoriePostDto) {

        Categorie categorie = categorieMapper.toEntity(categoriePostDto);
        categorie.setTenantId(TenantContext.getTenantId());
        return categorieRepository.save(categorie);
    }


    public Categorie update(CategorieUpdateDto categorieUpdateDto, Long id) {

        Categorie categorie = this.findById(id);

        categorieMapper.updateCategorieFromDto(categorieUpdateDto, categorie);

        return categorieRepository.save(categorie);
    }



    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }





}
