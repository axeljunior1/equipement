package com.projet.equipement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {
    @Id
    @Column(name = "ID_UTILISATEUR")
    private Long id;
    private String nom;
    private String email;
    public Role role;
    private String dateCreation;



}

