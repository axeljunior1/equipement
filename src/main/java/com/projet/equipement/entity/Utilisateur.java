package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {
    @GeneratedValue
    @Id
    @Column(name = "ID_UTILISATEUR")
    private Long id;
    private String nom;
    private String email;
    @OneToMany
    public List<Role> role;
    private String dateCreation;



}

