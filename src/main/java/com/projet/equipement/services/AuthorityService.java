package com.projet.equipement.services;


import com.projet.equipement.entity.Authority;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public Authority findById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority", id));
    }

    public Authority findById(String nom) {
        return authorityRepository.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Authority", String.valueOf(nom)));
    }


    public Authority save(Authority authority) {
        System.out.println(authorityRepository.findByNom(authority.getNom()));
        return authorityRepository.save(authority);
    }

    public void deleteById(Long id) {
        authorityRepository.deleteById(id);
    }




}
