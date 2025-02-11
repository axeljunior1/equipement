package com.projet.equipement.services;


import com.projet.equipement.dto.role.achat.RolePostDto;
import com.projet.equipement.dto.role.achat.RoleUpdateDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.RoleMapper;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));
    }

    public Role findByNom(String  nom) {
        return roleRepository.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Role", String.valueOf(nom)));
    }


    public Role save(RolePostDto rolePostDto) {
        Set<Authority> authorities = rolePostDto.getAutoritiesId().stream().map(authorityService::findById).collect(Collectors.toSet());

        Role role = roleMapper.postRoleDto(rolePostDto, authorities);

        return roleRepository.save(role);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Long id, RoleUpdateDto roleUpdateDto) {
        Role role = this.findById(id);
        Set<Authority> authorities = null ;
        if (roleUpdateDto.getAuthoritiesNoms() != null) {
            authorities = new HashSet<>(roleUpdateDto.getAuthoritiesNoms().stream().map(authorityService::findByNom).collect(Collectors.toSet()));
        }
        roleMapper.updateRoleFromDto(roleUpdateDto, role, authorities);

        return roleRepository.save(role);
    }

    public Role put(Long id, RoleUpdateDto roleUpdateDto){
        Role role = this.findById(id);
        Set<Authority> authorities = null ;
        if (roleUpdateDto.getAuthoritiesNoms() != null) {
            authorities = roleUpdateDto.getAuthoritiesNoms().stream().map(authorityService::findByNom).collect(Collectors.toSet());
        }
        roleMapper.updateRoleFromDto(roleUpdateDto, role, authorities);
        return roleRepository.save(role);
    }

    @Transactional
    public void deleteById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));

        for (Employe employe : role.getEmployes()) {
            employe.getRoles().remove(role);
        }

        // Supprimer toutes les liaisons dans la table role_authority
        role.getAuthorities().clear();
        roleRepository.save(role);

        // Maintenant, supprimer le rôle
        roleRepository.delete(role);
    }




}
