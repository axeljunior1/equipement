package com.projet.equipement.security;


import com.projet.equipement.entity.Employe;
import com.projet.equipement.repository.EmployeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeRepository employeRepository;

    public EmployeeDetailsService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employe employe = employeRepository.findByNom(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        return User.builder()
                .username(employe.getNom())
                .password(employe.getPassword()) // Spring Security vérifiera automatiquement le mot de passe
                .roles("user")
                .build();
    }
}
