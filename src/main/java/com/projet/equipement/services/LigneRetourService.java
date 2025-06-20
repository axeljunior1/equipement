package com.projet.equipement.services;


import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.entity.LigneRetour;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Retour;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneRetourMapper;
import com.projet.equipement.repository.LigneRetourRepository;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.RetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LigneRetourService {


    private final LigneRetourRepository ligneRetourRepository;
    private final LigneRetourMapper ligneRetourMapper;
    private final RetourRepository retourRepository;
    private final LigneVenteRepository ligneVenteRepository;

    public LigneRetourService(LigneRetourRepository ligneRetourRepository,
                                   LigneRetourMapper ligneRetourMapper,
                                   RetourRepository retourRepository,
                                   LigneVenteRepository ligneVenteRepository) {
        this.ligneRetourRepository = ligneRetourRepository;
        this.ligneRetourMapper = ligneRetourMapper;
        this.retourRepository = retourRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    public LigneRetourGetDto findById(Long id){
        return ligneRetourMapper.toDto(ligneRetourRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("LigneRetour", id)));
    }
    
    // ligneRetourne tout
    public Page<LigneRetourGetDto> findAll(Pageable pageable){
        Page<LigneRetour> all = ligneRetourRepository.findAll(pageable);
        return all.map(ligneRetourMapper::toDto);
    }
    
    //save
    public LigneRetourGetDto save(LigneRetour ligneRetour){
        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }
    
    
    //save
    public LigneRetourGetDto save(LigneRetourPostDto ligneRetourPostDto){
        Retour retour = retourRepository.findById(ligneRetourPostDto.getRetourId()).orElseThrow(
                ()-> new EntityNotFoundException("Retour", ligneRetourPostDto.getRetourId()));
        LigneVente ligneVente = ligneVenteRepository.findById(ligneRetourPostDto.getLigneVenteId()).orElseThrow(
                ()-> new EntityNotFoundException("LigneVente", ligneRetourPostDto.getLigneVenteId()));

        LigneRetour ligneRetour = ligneRetourMapper.toEntity(ligneRetourPostDto);
        
        ligneRetour.setRetour(retour);
        ligneRetour.setLigneVente(ligneVente);

        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }
    
    //modifier
    public LigneRetourGetDto update(LigneRetourUpdateDto ligneRetourUpdateDto, Long id){
        LigneRetour ligneRetour = ligneRetourRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("LigneRetour", id) );



        Retour retour = retourRepository.findById(ligneRetourUpdateDto.getRetourId()).orElseThrow(
                ()-> new EntityNotFoundException("Retour", ligneRetourUpdateDto.getRetourId()));
        LigneVente ligneVente = ligneVenteRepository.findById(ligneRetourUpdateDto.getLigneVenteId()).orElseThrow(
                ()-> new EntityNotFoundException("LigneVente", ligneRetourUpdateDto.getLigneVenteId()));

        ligneRetourMapper.updateDto(ligneRetourUpdateDto, ligneRetour);

        ligneRetour.setRetour(retour);
        ligneRetour.setLigneVente(ligneVente);
        
        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }
    
    //delete
    public void deleteById(Long id){
        ligneRetourRepository.deleteById(id);
    }


}
