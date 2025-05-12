package com.projet.equipement.services;


import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.AuthorityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Page<Authority> findAll(Pageable pageable) {
        return authorityRepository.findAll(pageable);
    }

    public Authority findById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority", id));
    }

    public Authority findByNom(String nom) {
        return authorityRepository.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Authority", String.valueOf(nom)));
    }


    public Authority save(Authority authority) {
        authority.setTenantId(TenantContext.getTenantId());
        return authorityRepository.save(authority);
    }

    public void deleteById(Long id) {
        authorityRepository.deleteById(id);
    }




}
