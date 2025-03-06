package com.projet.equipement.services;

import com.projet.equipement.dto.tarifAchat.TarifAchatGetDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatUpdateDto;
import com.projet.equipement.entity.TarifAchat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.TarifAchatMapper;
import com.projet.equipement.repository.TarifAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifAchatService {

    private final TarifAchatRepository tarifAchatRepository;
    private final TarifAchatMapper tarifAchatMapper;

    public TarifAchatService(TarifAchatRepository tarifAchatRepository, TarifAchatMapper tarifAchatMapper) {
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


    public TarifAchatGetDto save(TarifAchatPostDto tarifAchat) {
        TarifAchat tarifAchat1 = tarifAchatMapper.toEntity(tarifAchat);
        return tarifAchatMapper.toGetDto(tarifAchatRepository.save(tarifAchat1));
    }

    public void update(TarifAchatUpdateDto tarifAchatUpdateDto, Long id) {
        TarifAchat tarifAchat = tarifAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("TarifAchat", id));

        tarifAchatMapper.updateProduitFromDto(tarifAchatUpdateDto, tarifAchat) ;

        tarifAchatRepository.save(tarifAchat);

    }
}
