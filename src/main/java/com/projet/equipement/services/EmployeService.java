package com.projet.equipement.services;


import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.EmployeMapper;
import com.projet.equipement.repository.EmployeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<EmployeGetDto> findAll(Pageable pageable) {
        return employeRepository.findAll(pageable).map(employeMapper::toDto);
    }

    public EmployeGetDto findById(Long id) {
        return employeMapper.toDto(employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe", id)));
    }


    public EmployeGetDto findByUsername(String username) {
        return employeMapper.toDto(employeRepository.findByNom(username)
                .orElseThrow(() -> new EntityNotFoundException("Employe", username)));
    }

    public Employe save(EmployePostDto employePostDto) {
        Set<Role> roles = employePostDto.getRolesIds().stream().map(roleService::findById).collect(Collectors.toSet());

        Employe employe = employeMapper.toEmploye(employePostDto);
        employe.setPassword(passwordEncoder.encode(employe.getPassword()));

        return employeRepository.save(employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }


    public EmployeGetDto updateEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe", id));
        employeMapper.updateEmployeFromDto(employeUpdateDto, employe);
        return employeMapper.toDto(employeRepository.save(employe));
    }

    public EmployeGetDto putEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe", id));
        employeMapper.updateEmployeFromDto(employeUpdateDto, employe);
        return employeMapper.toDto(employeRepository.save(employe));
    }

    // Add a role to a user
//    public void addRoleToUser(Long userId, Long roleId) {
//        Employe employe = findById(userId);
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        // Add the role to the user
//        employe.getRoles().add(role);
//        employeRepository.save(employe);
//    }

}
