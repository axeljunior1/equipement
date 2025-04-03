package com.projet.equipement.services;

import com.projet.equipement.dto.tarifAchat.TarifAchatGetDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatUpdateDto;
import com.projet.equipement.entity.TarifAchat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.TarifAchatMapper;
import com.projet.equipement.repository.TarifAchatRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarifAchatService {

    private final TarifAchatRepository tarifAchatRepository;
    private final TarifAchatMapper tarifAchatMapper;

    public TarifAchatService(TarifAchatRepository tarifAchatRepository,
                             TarifAchatMapper tarifAchatMapper
    ) {
        this.tarifAchatRepository = tarifAchatRepository;
        this.tarifAchatMapper = tarifAchatMapper;
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

         tarifAchatRepository.save(tarifAchat1);

        return new TarifAchatGetDto();
    }
    @Transactional
    public TarifAchat save(TarifAchat tarifAchat) {

        return tarifAchatRepository.save(tarifAchat);
    }

    public TarifAchatGetDto updateTarifAchat(TarifAchatUpdateDto tarifAchatUpdateDto, Long id) {
        TarifAchat tarifAchat = tarifAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("TarifAchat", id));

        tarifAchatMapper.updateProduitFromDto(tarifAchatUpdateDto, tarifAchat) ;

        return tarifAchatMapper.toGetDto(tarifAchatRepository.save(tarifAchat));

    }

    public TarifAchat findByProduitId(Long produitId) {

        return tarifAchatRepository.findByProduitId(produitId).orElseThrow(()->
                new EntityNotFoundException("TarifAchat => ProduitId", produitId));
    }
}
