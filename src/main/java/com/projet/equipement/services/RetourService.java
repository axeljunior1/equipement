package com.projet.equipement.services;


import com.projet.equipement.dto.retour.RetourGetDto;
import com.projet.equipement.dto.retour.RetourPostDto;
import com.projet.equipement.dto.retour.RetourUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.RetourMapper;
import com.projet.equipement.repository.EtatRetourRepository;
import com.projet.equipement.repository.RetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RetourService {


    private final RetourRepository retourRepository;
    private final RetourMapper retourMapper;
    private final EtatRetourService etatRetourService;
    private final VenteService venteService;
    private final TypeRetourService typeRetourService;

    public RetourService(RetourRepository retourRepository,
                         RetourMapper retourMapper,
                         EtatRetourService etatRetourService,
                         VenteService venteService,
                         TypeRetourService typeRetourService) {
        this.retourRepository = retourRepository;
        this.retourMapper = retourMapper;
        this.etatRetourService = etatRetourService;
        this.venteService = venteService;
        this.typeRetourService = typeRetourService;
    }

    public RetourGetDto findById(Long id){
        return retourMapper.toDto(retourRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Retour", id)));
    }
    
    // retourne tout
    public Page<RetourGetDto> findAll(Pageable pageable){
        Page<Retour> all = retourRepository.findAll(pageable);
        return all.map(retourMapper::toDto);
    }
    
    //save
    public RetourGetDto save(Retour retour){
        retour.setTenantId(TenantContext.getTenantId());
        return retourMapper.toDto(retourRepository.save(retour));
    }


    //save
    public RetourGetDto save(RetourPostDto retourPostDto){
        Vente vente = venteService.findById(retourPostDto.getVenteId());
        EtatRetour etatRetour = etatRetourService.findById(retourPostDto.getEtatId());
        TypeRetour typeRetour = typeRetourService.findById(retourPostDto.getTypeId());
        Retour retour = retourMapper.toEntity(retourPostDto);

        retour.setVente(vente);
        retour.setEtat(etatRetour);
        retour.setTypeRetour(typeRetour);

        return retourMapper.toDto(retourRepository.save(retour));
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
    
    //delete
    public void deleteById(Long id){
        retourRepository.deleteById(id);
    }


}
