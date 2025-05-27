package com.projet.equipement.services;


import com.projet.equipement.dto.devise.DevisePostDto;
import com.projet.equipement.dto.devise.DeviseUpdateDto;
import com.projet.equipement.entity.Devise;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.DeviseMapper;
import com.projet.equipement.repository.DeviseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeviseService {
    private final DeviseRepository deviseRepository;
    private final DeviseMapper deviseMapper;

    public DeviseService(DeviseRepository deviseRepository, DeviseMapper deviseMapper) {
        this.deviseRepository = deviseRepository;
        this.deviseMapper = deviseMapper;
    }

    public Page<Devise> findAll(Pageable pageable) {
        return deviseRepository.findAll(pageable);
    }
    public Devise findByCode(String code) {
        return deviseRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("Devise", code));
    }

    public Devise findByNom(String nom) {
        return deviseRepository.findByNom(nom).orElseThrow(()-> new EntityNotFoundException("Devise", nom));
    }



    public Devise findById(Long id) {
        return deviseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devise", id));
    }

    public Devise save(DevisePostDto devisePostDto) {

        Devise devise = deviseMapper.toEntity(devisePostDto);
        return deviseRepository.save(devise);
    }


    public Devise update(DeviseUpdateDto deviseUpdateDto, Long id) {

        Devise devise = this.findById(id);

        deviseMapper.updateDeviseFromDto(deviseUpdateDto, devise);

        return deviseRepository.save(devise);
    }



    public void deleteById(Long id) {
        deviseRepository.deleteById(id);
    }





}
