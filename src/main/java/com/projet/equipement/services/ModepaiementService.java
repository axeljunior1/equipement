package com.projet.equipement.services;

import com.projet.equipement.dto.modePaiement.ModePaiementGetDto;
import com.projet.equipement.dto.modePaiement.ModePaiementPostDto;
import com.projet.equipement.dto.modePaiement.ModePaiementUpdateDto;
import com.projet.equipement.entity.ModePaiement;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ModePaiementMapper;
import com.projet.equipement.repository.ModePaimentRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ModepaiementService {

    private final ModePaimentRepository modePaimentRepository;
    private final ModePaiementMapper modePaiementMapper;

    public ModepaiementService(ModePaimentRepository modePaimentRepository, ModePaiementMapper modePaiementMapper) {
        this.modePaimentRepository = modePaimentRepository;
        this.modePaiementMapper = modePaiementMapper;
    }

    public ModePaiement getModePaiement(Long id) {
        return modePaimentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mode paiement", id));
    }

    public ModePaiement getModePaiementByCode(String code) {
        return modePaimentRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Mode paiement", code));
    }

    public ModePaiementGetDto save(ModePaiement modePaiement) {
        modePaiement.setTenantId(TenantContext.getTenantId());
        ModePaiement mpSaved = modePaimentRepository.save(modePaiement);
        return modePaiementMapper.toDto(mpSaved);
    }


    public ModePaiementGetDto save(ModePaiementPostDto modePaiementPostDto) {

        ModePaiement mpEntity = modePaiementMapper.toEntity(modePaiementPostDto);
        mpEntity.setActive(true);

        return save(mpEntity);
    }

    public void delete(ModePaiement modePaiement) {
        modePaimentRepository.delete(modePaiement);
    }

    public Page<ModePaiementGetDto> findAll(Pageable pageable) {
        Page<ModePaiement> allByPages = modePaimentRepository.findAll(pageable);
        return allByPages.map(modePaiementMapper::toDto);
    }


    public ModePaiement update(Long id, @Valid ModePaiementUpdateDto updatedData) {
        ModePaiement modePaiement = modePaimentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mode paiement", id));
        modePaiementMapper.updateDto(updatedData, modePaiement);
        return modePaimentRepository.save(modePaiement);
    }
}
