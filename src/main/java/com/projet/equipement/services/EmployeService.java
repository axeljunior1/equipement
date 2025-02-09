package com.projet.equipement.services;


import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.EmployeMapper;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;
    private final RoleRepository roleRepository;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper, RoleRepository roleRepository) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
        this.roleRepository = roleRepository;
    }

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe", id));
    }


    public Employe findByUsername(String username) {
        return employeRepository.findByNom(username)
                .orElseThrow(() -> new EntityNotFoundException("Employe", username));
    }

    public Employe save(EmployePostDto employePostDto) {
        Set<Role> roles = employePostDto.getRoles().stream().map(roleName -> roleRepository.findByNom(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role", String.valueOf(roleName)))
        ).collect(Collectors.toSet());

        Employe employe = employeMapper.postEmployeDto(employePostDto, roles);

        return employeRepository.save(employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }


    public Employe updateEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        Employe employe = findById(id);
//        Set<Role> roles = new HashSet<>();
        employeMapper.updateEmployeFromDto(employeUpdateDto, employe);
        return employeRepository.save(employe);
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
