package com.projet.equipement.security;


import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.TenantRepository;
import com.projet.equipement.services.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeDetailsService implements UserDetailsService {


    private final EmployeRepository employeRepository;
    private final RoleService roleService;
    private final TenantRepository tenantRepository;

    public EmployeeDetailsService(EmployeRepository employeRepository, RoleService roleService, TenantRepository tenantRepository) {
        this.employeRepository = employeRepository;
        this.roleService = roleService;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || !tenantRepository.existsByIdAndIsActiveTrue(tenantId)) {
            throw new EntityNotFoundException("Tenant / Entité",tenantId );
        }

        Employe employe = employeRepository.findByNom(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        // Récupérer les rôles et les convertir en authorities
        Set<GrantedAuthority> authorities = employe.getEmployeRoles().stream()
                .flatMap(role -> {
                    // Ajoute le rôle en tant qu'autorité avec le préfixe "ROLE_"
                    Set<GrantedAuthority> roleAuthorities = new HashSet<>();
                    roleAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleService.findById(role.getRole().getId()).getNom()));

                    // Ajoute les permissions spécifiques du rôle
                    if (role.getRole().getAuthoritiesRole() != null && !role.getRole().getAuthoritiesRole().isEmpty()) {
                        roleAuthorities.addAll(role.getRole().getAuthoritiesRole().stream()
                                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority().getNom()))
                                .collect(Collectors.toSet()));
                    }
                    return roleAuthorities.stream();
                })
                .collect(Collectors.toSet());

        return User.builder()
                .username(employe.getNom())
                .password(employe.getPassword()) // Spring Security gère la vérification du mot de passe
                .authorities(authorities) // Utilisation correcte des authorities
                .build();

    }
}

