package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_UTILISATEUR")
    private Long id;
    private String nom;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "utilisateurs_roles",  // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "id_utilisateur"),  // Colonne de jointure dans utilisateurs_role pour l'utilisateur
            inverseJoinColumns = @JoinColumn(name = "id_role")  // Colonne de jointure dans utilisateurs_role pour le rôle
    )
    private Set<Role> roles = new HashSet<>();
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;



}

