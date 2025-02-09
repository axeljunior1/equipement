package com.projet.equipement.services;


import com.projet.equipement.dto.role.achat.RolePostDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.RoleMapper;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthorityService authorityService;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, AuthorityService authorityService, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.authorityService = authorityService;
        this.roleMapper = roleMapper;
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


    public Role save(RolePostDto rolePostDto) {
        Set<Authority> authorities = rolePostDto.getAutoritiesId().stream().map(authorityService::findById).collect(Collectors.toSet());

        Role role = roleMapper.postRoleDto(rolePostDto, authorities);

        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }




}
