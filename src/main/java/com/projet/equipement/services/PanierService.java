package com.projet.equipement.services;


import com.projet.equipement.dto.panier.PanierGetDto;
import com.projet.equipement.dto.panier.PanierPostDto;
import com.projet.equipement.entity.Panier;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.PanierMapper;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.EtatPanierRepository;
import com.projet.equipement.repository.PanierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PanierService {
    private final PanierRepository panierRepository;
    private final PanierMapper panierMapper;
    private final EtatPanierRepository etatPanierRepository;
    private final EmployeRepository employeRepository;

    public PanierService(PanierRepository panierRepository, PanierMapper panierMapper, EtatPanierRepository etatPanierRepository, EmployeRepository employeRepository) {
        this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
        this.etatPanierRepository = etatPanierRepository;
        this.employeRepository = employeRepository;
    }

    public Page<Panier> findAll(Pageable pageable) {
        return panierRepository.findAll(pageable);
    }

    public List<Panier> findAllByEmployeId(Long id) {
        return panierRepository.findByEmployeId(id);
    }

    public Panier findById(Long id) {
        return panierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Panier", id));
    }



    @Transactional
    public PanierGetDto save(PanierPostDto panier) {
        Panier entity = panierMapper.toEntity(panier);
        entity.setEtat(etatPanierRepository.findById(panier.getEtatId()).orElseThrow(()->new EntityNotFoundException("Etat Panier", panier.getEtatId())));
        entity.setEmploye(employeRepository.findById(panier.getEmployeId()).orElseThrow(()->new EntityNotFoundException("Employe", panier.getEmployeId())));
        entity.setTenantId(TenantContext.getTenantId());

        Panier save = panierRepository.save(entity);

        return panierMapper.toGetDto(save);
    }

    public void deleteById(Long id) {
        panierRepository.deleteById(id);
    }



    public Panier updatePanier(Panier panierToUpdate, Long id) {
        Panier panier = findById(id);
        if (panierToUpdate  != null) {
            panier.setEtat(panierToUpdate.getEtat());
        }
        return panierRepository.save(panier);
    }


}
