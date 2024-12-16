package com.projet.equipement.mapper;

import com.projet.equipement.dto.utilisateur.UtilisateurUpdateDto;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UtilisateurMapper {

//    private String nom;
//    private String email;
//
//    private Set<Long> roleIds;
//
//    private LocalDateTime dateCreation;


    public void updateUtilisateurFromDto(UtilisateurUpdateDto utilisateurUpdateDto, Utilisateur utilisateur, Set<Role> roles){
        if (utilisateurUpdateDto.getNom() != null) utilisateur.setNom(utilisateurUpdateDto.getNom());
        if (utilisateurUpdateDto.getEmail() != null) utilisateur.setEmail(utilisateurUpdateDto.getEmail());
        if (utilisateurUpdateDto.getDateCreation() != null) utilisateur.setDateCreation(utilisateurUpdateDto.getDateCreation());
        // si les ID son presents alors on set les roles
        if (utilisateurUpdateDto.getRoleIds() != null) utilisateur.setRoles(roles);
    }
}
