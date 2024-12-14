package com.projet.equipement.services;


import com.projet.equipement.entity.Role;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Map<String, Object> patchRole) {
        Role role = findById(id);

        patchRole.forEach(
                (key, value) -> {
                    if (value != null) {
                        switch (key) {
                            case "nom":
                                role.setNom((String) value);
                                break;
                            case "description":
                                role.setDescription((String) value);
                                break;
                            // Ajoutez d'autres champs au besoin
                        }
                    }
                });
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
