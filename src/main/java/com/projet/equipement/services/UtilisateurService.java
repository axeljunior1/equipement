package com.projet.equipement.services;


import com.projet.equipement.dto.utilisateur.UtilisateurUpdateDto;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.Utilisateur;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.UtilisateurMapper;
import com.projet.equipement.repository.RoleRepository;
import com.projet.equipement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur", id));
    }

    public Utilisateur save(Utilisateur utilisateur) {
        Set<Role> roles = utilisateur.getRoles();

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // Update an existing user
/*
    @Transactional
    public Utilisateur updateUtilisateur(Long id, Map<String, Object> updates)  {
        Utilisateur utilisateurOld = findById(id);
        Utilisateur utilisateur = findById(id);
        updates.forEach((key, value) -> {
            if ("nom".equals(key)) {
                utilisateur.setNom((String) value);
            } else if ("roles".equals(key) && value instanceof List<?> list) {
                Set<Role> roleSet = new HashSet<>();
                list.forEach(item -> {
                    if (item instanceof Map<?, ?> roleMap) {
                        Role role = new Role();
                        roleMap.forEach((k, v) -> {
                            if ("id_role".equals(k)) {
                                role.setId_role(Long.valueOf(v.toString()));
                            } else if ("nom".equals(k)) {
                                role.setNom(v.toString());
                            }
                        });
                        roleSet.add(role);
                    }
                });
                utilisateur.setRoles(roleSet);
            }
        });

        // Pour chaque champ à mettre à jour, vérifiez s'il existe dans le Map
        return utilisateurRepository.save(utilisateur);
    }
*/


    @Transactional
    public Utilisateur updateUtilisateur(UtilisateurUpdateDto utilisateurUpdateDto, Long id) {
        Utilisateur utilisateur = findById(id);
        Set<Role> roles = new HashSet<>();
        if (utilisateurUpdateDto.getRoleIds() != null) {
            for (Long idDto : utilisateurUpdateDto.getRoleIds()) {
                Role role = roleService.findById(idDto);
                roles.add(role);
            }
        }

        utilisateurMapper.updateUtilisateurFromDto(utilisateurUpdateDto,utilisateur, roles);
        return utilisateurRepository.save(utilisateur);
    }

    // Add a role to a user
    public void addRoleToUser(Long userId, Long roleId) {
        Utilisateur utilisateur = findById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Add the role to the user
        utilisateur.getRoles().add(role);
        utilisateurRepository.save(utilisateur);
    }

}
