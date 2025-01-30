package com.projet.equipement.services;


import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.EmployeMapper;
import com.projet.equipement.repository.EmployeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
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
//        Set<Role> roles = employe.getRoles();
       Employe employe =  employeMapper.postEmployeDto(employePostDto);

        return employeRepository.save(employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }



    public Employe updateEmploye(EmployeUpdateDto employeUpdateDto, Long id) {
        Employe employe = findById(id);
//        Set<Role> roles = new HashSet<>();
        employeMapper.updateEmployeFromDto(employeUpdateDto,employe);
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
