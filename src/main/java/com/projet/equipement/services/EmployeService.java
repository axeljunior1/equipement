package com.projet.equipement.services;


import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.EmployeMapper;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.RoleEmployeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleEmployeService roleEmployeService;

    public EmployeService(EmployeRepository employeRepository,
                          EmployeMapper employeMapper,
                          EntityManager entityManager,
                          PasswordEncoder passwordEncoder,
                          RoleEmployeService roleEmployeService) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.roleEmployeService = roleEmployeService;
    }

    public Page<EmployeGetDto> findAll(Pageable pageable) {
        return employeRepository.findAll(pageable, TenantContext.getTenantId()).map(employeMapper::toDto);
    }

    public EmployeGetDto findById(Long id) {
        return employeMapper.toDto(employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe", id)));
    }


    public Employe findByIdEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe", id));
    }


    public EmployeGetDto findByUsername(String username) {
        return employeMapper.toDto(employeRepository.findByNom(username)
                .orElseThrow(() -> new EntityNotFoundException("Employe", username)));
    }

    @Transactional
    public Employe save(EmployePostDto employePostDto) {
// 1. Mapper et encoder
        Employe employe = employeMapper.toEmploye(employePostDto);
        String tenantId = TenantContext.getTenantId();
        employe.setTenantId(tenantId);
        employe.setPassword(passwordEncoder.encode(employe.getPassword()));

// 2. Sauver pour obtenir l'ID
        Employe employeSave = employeRepository.save(employe);

        roleEmployeService.save(tenantId, employe, employePostDto.getRolesIds());

        return employeRepository.save(employeSave);
    }

    public Employe save(Employe employe) {
        employe.setPassword(passwordEncoder.encode(employe.getPassword()));

        employe.setTenantId(TenantContext.getTenantId());
        return employeRepository.save(employe);
    }

    public Employe save(Employe employe, String tenantId) {
        employe.setPassword(passwordEncoder.encode(employe.getPassword()));

        employe.setTenantId(tenantId);
        return employeRepository.save(employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }


    @Transactional
    public EmployeGetDto updateEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        // 1. Récupérer l'employé
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe", id));

        employeMapper.updateEmployeFromDto(employeUpdateDto, employe);

        Employe saved = employeRepository.save(employe);

        // 4. Gérer les rôles
        roleEmployeService.deleteByEmployeId(id);

        entityManager.clear();
        roleEmployeService.save(TenantContext.getTenantId(), employe, employeUpdateDto.getRolesIds());

        // 5. Retourner le DTO
        return employeMapper.toDto(saved);
    }

    public EmployeGetDto putEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe", id));
        employeMapper.updateEmployeFromDto(employeUpdateDto, employe);
        return employeMapper.toDto(employeRepository.save(employe));
    }


}