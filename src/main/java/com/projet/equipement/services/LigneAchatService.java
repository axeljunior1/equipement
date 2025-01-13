package com.projet.equipement.services;


import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneAchatMapper;
import com.projet.equipement.repository.LigneAchatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneAchatService {

    private final LigneAchatRepository ligneAchatRepository;
    private final LigneAchatMapper ligneAchatMapper;
    private final AchatService achatService;
    private final ProduitService produitService;

    public LigneAchatService(LigneAchatRepository ligneAchatRepository, LigneAchatMapper ligneAchatMapper, AchatService achatService, ProduitService produitService) {
        this.ligneAchatRepository = ligneAchatRepository;
        this.ligneAchatMapper = ligneAchatMapper;
        this.achatService = achatService;
        this.produitService = produitService;
    }

    public List<LigneAchat> findAll(){
        return ligneAchatRepository.findAll();
    }

    public  LigneAchat findById(Long id){
        return  ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id));
    }

    public LigneAchat save(LigneAchatPostDto ligneAchatPostDto){
        LigneAchat ligneAchat = ligneAchatMapper.postLigneAchatFromDto(ligneAchatPostDto, achatService, produitService);
        return ligneAchatRepository.save(ligneAchat);
    }


    public LigneAchat updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id){
        LigneAchat ligneAchat = findById(id);
        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat, achatService, produitService);
        return ligneAchatRepository.save(ligneAchat);
    }

    public void deleteById(Long id){
        ligneAchatRepository.deleteById(id);
    }

}
