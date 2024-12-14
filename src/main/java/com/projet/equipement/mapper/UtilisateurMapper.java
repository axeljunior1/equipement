package com.projet.equipement.mapper;

import com.projet.equipement.dto.ClientDto;
import com.projet.equipement.dto.UtilisateurDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class UtilisateurMapper {

//    private String nom;
//    private String email;
//
//    private Set<Long> roleIds;
//
//    private LocalDateTime dateCreation;


    public void updateUtilisateurFromDto(UtilisateurDto utilisateurDto, Utilisateur utilisateur, Set<Role> roles){
        if (utilisateurDto.getNom() != null) utilisateur.setNom(utilisateurDto.getNom());
        if (utilisateurDto.getEmail() != null) utilisateur.setEmail(utilisateurDto.getEmail());
        if (utilisateurDto.getDateCreation() != null) utilisateur.setDateCreation(utilisateurDto.getDateCreation());
        // si les ID son presents alors on set les roles
        if (utilisateurDto.getRoleIds() != null) utilisateur.setRoles(roles);
    }
}
