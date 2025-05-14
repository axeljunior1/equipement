package com.projet.equipement.services;

import com.projet.equipement.dto.tarifAchat.TarifAchatGetDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TarifAchat;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.TarifAchatMapper;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.TarifAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarifAchatService {

    private final TarifAchatRepository tarifAchatRepository;
    private final TarifAchatMapper tarifAchatMapper;
    private final ProduitRepository produitRepository;

    public TarifAchatService(TarifAchatRepository tarifAchatRepository,
                             TarifAchatMapper tarifAchatMapper,
                             ProduitRepository produitRepository) {
        this.tarifAchatRepository = tarifAchatRepository;
        this.tarifAchatMapper = tarifAchatMapper;
        this.produitRepository = produitRepository;
    }

    public TarifAchatGetDto findById(Long id) {
        return tarifAchatMapper.toGetDto(tarifAchatRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("TarifAchat", id)));
    }

    
    public Page<TarifAchatGetDto> findAll(Pageable pageable) {
        return tarifAchatRepository.findAll(pageable).map(tarifAchatMapper::toGetDto);
    }


    @Transactional
    public TarifAchatGetDto save(TarifAchatPostDto tarifAchat) {
        TarifAchat tarifAchat1 = tarifAchatMapper.toEntity(tarifAchat);

        tarifAchat1.setTenantId(TenantContext.getTenantId());
         tarifAchatRepository.save(tarifAchat1);

        return new TarifAchatGetDto();
    }
    @Transactional
    public TarifAchat save(TarifAchat tarifAchat) {

        tarifAchat.setTenantId(TenantContext.getTenantId());
        return tarifAchatRepository.save(tarifAchat);
    }

    public TarifAchatGetDto updateTarifAchat(TarifAchatUpdateDto tarifAchatUpdateDto, Long id) {
        TarifAchat tarifAchat = tarifAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("TarifAchat", id));

        tarifAchatMapper.updateProduitFromDto(tarifAchatUpdateDto, tarifAchat) ;

        return tarifAchatMapper.toGetDto(tarifAchatRepository.save(tarifAchat));

    }

    public TarifAchat findByProduitId(Long produitId) {


        Produit produit = produitRepository.findById(produitId).orElseThrow(()-> new EntityNotFoundException("Produit", produitId));
        return tarifAchatRepository.findByProduit(produit).orElseThrow(()->
                new EntityNotFoundException("TarifAchat => ProduitId", produitId));
    }
}
