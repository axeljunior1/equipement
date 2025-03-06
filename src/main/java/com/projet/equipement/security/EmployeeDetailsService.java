package com.projet.equipement.security;


import com.projet.equipement.entity.Employe;
import com.projet.equipement.repository.EmployeRepository;
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

    public EmployeeDetailsService(EmployeRepository employeRepository, RoleService roleService) {
        this.employeRepository = employeRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employe employe = employeRepository.findByNom(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        // Récupérer les rôles et les convertir en authorities
        Set<GrantedAuthority> authorities = employe.getRoles().stream()
                .flatMap(role -> {
                    // Ajoute le rôle en tant qu'autorité avec le préfixe "ROLE_"
                    Set<GrantedAuthority> roleAuthorities = new HashSet<>();
                    roleAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleService.findById(role.getId()).getNom()));

                    // Ajoute les permissions spécifiques du rôle
                    if (role.getAuthorities() != null) {
                        roleAuthorities.addAll(role.getAuthorities().stream()
                                .map(auth -> new SimpleGrantedAuthority(auth.getNom()))
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
