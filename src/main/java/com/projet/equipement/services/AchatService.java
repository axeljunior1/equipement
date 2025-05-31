package com.projet.equipement.services;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.repository.AchatRepository;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.LigneAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AchatService {

    private final AchatRepository achatRepository;
    private final AchatMapper achatMapper;
    private final EmployeRepository employeRepository;
    private final LigneAchatRepository ligneAchatRepository;
    private final LigneAchatService ligneAchatService;

    public AchatService(AchatRepository achatRepository, AchatMapper achatMapper, EmployeRepository employeRepository, LigneAchatRepository ligneAchatRepository, LigneAchatService ligneAchatService) {
        this.achatRepository = achatRepository;
        this.achatMapper = achatMapper;
        this.employeRepository = employeRepository;
        this.ligneAchatRepository = ligneAchatRepository;
        this.ligneAchatService = ligneAchatService;
    }

    public Page<AchatGetDto> findAll(Pageable pageable) {
        Page<Achat> allPage = achatRepository.findAllPage(pageable);
        return allPage.map(achatMapper::toDto);
    }

    public  AchatGetDto findById(Long id) {
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));
        return achatMapper.toDto(achat);
    }

    public AchatGetDto getAchatById(Long id){
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));
        return achatMapper.toDto(achat);
    }

    public AchatGetDto save(AchatPostDto achatPostDto){
        Achat achat = achatMapper.toEntity(achatPostDto);
        achat.setEmploye(employeRepository.findById(achatPostDto.getEmployeId()).orElseThrow(()-> new EntityNotFoundException("Employe", achatPostDto.getEmployeId())));
        achat.setTenantId(TenantContext.getTenantId());
        achat.setMontantTotal(0.0);
        return achatMapper.toDto(achatRepository.save(achat));
    }

    public AchatGetDto update(AchatUpdateDto achatUpdateDto, Long id){
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));

        achatMapper.updateDto(achatUpdateDto, achat);

        return achatMapper.toDto(achatRepository.save(achat));
    }

    @Transactional
    public void softDeleteAchat(Long id) {

        List<LigneAchat> ligneAchats = ligneAchatRepository.findByAchatId(id);
        for (LigneAchat ligneAchat : ligneAchats) {
            ligneAchatService.deleteLinesByIdSoft(ligneAchat.getId());
        }

        this.deleteAchatByIdSoft(id);
    }

    public void deleteAchatByIdSoft(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        achat.setActif(false);  // Soft delete
        achatRepository.save(achat);
    }



}
