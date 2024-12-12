package com.projet.equipement.services;


import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Role;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
    public Optional<Role> findById(int id){
        return Optional.of(roleRepository.findById(id)).get();
    }
    public Role save(Role role){
        return roleRepository.save(role);
    }
    public void deleteById(int id){
        roleRepository.deleteById(id);
    }

}
