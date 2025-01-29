package com.projet.equipement.services;


import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.repository.LigneVenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final LigneVenteMapper ligneVenteMapper;
    private final VenteService venteService;
    private final ProduitService produitService;

    public LigneVenteService(LigneVenteRepository ligneVenteRepository, LigneVenteMapper ligneVenteMapper, VenteService venteService, ProduitService produitService) {
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneVenteMapper = ligneVenteMapper;
        this.venteService = venteService;
        this.produitService = produitService;
    }

    public Page<LigneVente> findAll(Pageable pageable){
        return ligneVenteRepository.findAll(pageable);
    }

    public  LigneVente findById(Long id){
        return  ligneVenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneVente", id));
    }

    public LigneVente save(LigneVentePostDto ligneVentePostDto){
        LigneVente ligneVente = ligneVenteMapper.postLigneVenteFromDto(ligneVentePostDto, venteService, produitService);
        return ligneVenteRepository.save(ligneVente);
    }


    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id){
        LigneVente ligneVente = findById(id);
        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente, venteService, produitService);
        return ligneVenteRepository.save(ligneVente);
    }

    public void deleteById(Long id){
        ligneVenteRepository.deleteById(id);
    }

}
