package com.projet.equipement.services;


import com.projet.equipement.constants.RefCodes;
import com.projet.equipement.dto.retour.RetourGetDto;
import com.projet.equipement.dto.retour.RetourLightGetDto;
import com.projet.equipement.dto.retour.RetourPostDto;
import com.projet.equipement.dto.retour.RetourUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.exceptions.InvalidOperationException;
import com.projet.equipement.mapper.LigneRetourMapper;
import com.projet.equipement.mapper.RetourMapper;
import com.projet.equipement.repository.EtatPanierRepository;
import com.projet.equipement.repository.EtatRetourRepository;
import com.projet.equipement.repository.LigneRetourRepository;
import com.projet.equipement.repository.RetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class RetourService {


    private final RetourRepository retourRepository;
    private final RetourMapper retourMapper;
    private final EtatRetourService etatRetourService;
    private final VenteService venteService;
    private final TypeRetourService typeRetourService;
    private final LigneRetourRepository ligneRetourRepository;
    private final LigneRetourMapper ligneRetourMapper;
    private final EtatPanierRepository etatPanierRepository;
    private final EtatRetourRepository etatRetourRepository;

    public RetourService(RetourRepository retourRepository,
                         RetourMapper retourMapper,
                         EtatRetourService etatRetourService,
                         VenteService venteService,
                         TypeRetourService typeRetourService, LigneRetourRepository ligneRetourRepository, LigneRetourMapper ligneRetourMapper, EtatPanierRepository etatPanierRepository, EtatRetourRepository etatRetourRepository) {
        this.retourRepository = retourRepository;
        this.retourMapper = retourMapper;
        this.etatRetourService = etatRetourService;
        this.venteService = venteService;
        this.typeRetourService = typeRetourService;
        this.ligneRetourRepository = ligneRetourRepository;
        this.ligneRetourMapper = ligneRetourMapper;
        this.etatPanierRepository = etatPanierRepository;
        this.etatRetourRepository = etatRetourRepository;
    }

    public RetourGetDto findById(Long id){
        RetourGetDto retour = retourMapper.toDto(retourRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Retour", id)));
        Page<LigneRetour> byRetourId = ligneRetourRepository.findByRetour_Id(retour.getId(), Pageable.unpaged());
        retour.setLigneRetours(byRetourId.stream().map(ligneRetourMapper::toDto).collect(Collectors.toList()));
        return retour;
    }
    
    // retourne tout
    public Page<RetourLightGetDto> findAll(Pageable pageable){
        Page<Retour> all = retourRepository.findAll(pageable);
        return all.map(retour -> {
            RetourLightGetDto retourLightGetDto = retourMapper.toLightDto(retour);
            Page<LigneRetour> byRetourId = ligneRetourRepository.findByRetour_Id(retourLightGetDto.getId(), Pageable.unpaged());
            retourLightGetDto.setLigneRetours(byRetourId.stream().map(ligneRetourMapper::toDto).collect(Collectors.toList()));
            return retourLightGetDto;
        });
    }
    
    //save
    public RetourGetDto save(Retour retour){
        retour.setTenantId(TenantContext.getTenantId());
        return retourMapper.toDto(retourRepository.save(retour));
    }


    //save
    public RetourGetDto save(RetourPostDto retourPostDto) throws InvalidOperationException {
        Vente vente = venteService.findById(retourPostDto.getVenteId());
        
        String codeEtatVente = vente.getEtat().getLibelle(); // ou getLibelle() selon ton modèle
        if (!estEtatVenteAutorisePourRetour(codeEtatVente)) {
            throw new InvalidOperationException(
                    "Impossible de créer un retour pour une vente avec l’état " + codeEtatVente
            );
        }

        EtatRetour etatRetour = etatRetourRepository.findByLibelle("EN_ATTENTE_VALIDATION").orElseThrow(
                () -> new EntityNotFoundException("Etat retour", retourPostDto.getVenteId())
        );
        TypeRetour typeRetour = typeRetourService.findById(retourPostDto.getTypeId());

        Retour retour = new Retour();

        retour.setVente(vente);
        retour.setEtat(etatRetour);
        retour.setTypeRetour(typeRetour);
        LocalDateTime date = LocalDateTime.now() ;
        retour.setDateCreation(date);

        return retourMapper.toDto(retourRepository.save(retour));
    }

    private boolean estEtatVenteAutorisePourRetour(String code) {
        if (code == null) return false;

        return switch (code) {
            case RefCodes.EtatVente.PAIEMENT_PARTIEL,
                 RefCodes.EtatVente.PAYEE,
                 RefCodes.EtatVente.VENTE_A_CREDIT -> true;
            default -> false;
        };
    }


    //modifier
    public RetourGetDto update(RetourUpdateDto retourUpdateDto, Long id){
        Retour retour = retourRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Retour", id) );


        Vente vente = venteService.findById(retourUpdateDto.getVenteId());
        EtatRetour etatRetour = etatRetourService.findById(retourUpdateDto.getEtatId());
        TypeRetour typeRetour = typeRetourService.findById(retourUpdateDto.getTypeId());
        retourMapper.updateDto(retourUpdateDto, retour);

        retour.setVente(vente);
        retour.setEtat(etatRetour);
        retour.setTypeRetour(typeRetour);

        return retourMapper.toDto(retourRepository.save(retour));
    }

    public RetourGetDto valider(Long id){
        Retour retour = retourRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Retour", id)
        );

        retour.setEtat(etatRetourRepository.findByLibelle("VALIDE").orElseThrow(
                () -> new EntityNotFoundException("Etat retour", "VALIDE")
        ));
        return retourMapper.toDto(retourRepository.save(retour));
    }

    public RetourGetDto rejeter(Long id){
        Retour retour = retourRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Retour", id)
        );

        retour.setEtat(etatRetourRepository.findByLibelle("REJETE").orElseThrow(
                () -> new EntityNotFoundException("Etat retour", "REJETE")
        ));
        return retourMapper.toDto(retourRepository.save(retour));
    }
    
    //delete
    public void deleteById(Long id){
        retourRepository.deleteById(id);
    }


}
