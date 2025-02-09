package com.projet.equipement.services;


import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));
    }

    public Role findById(String  nom) {
        return roleRepository.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Role", String.valueOf(nom)));
    }


    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }




}
