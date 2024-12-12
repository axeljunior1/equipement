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
@Table(name = "FOURNISSEURS")
public class Fournisseur {

    @Column(name = "ID_FOURNISSEUR")
    @Id
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "ADRESSE")
    private String adresse;
}

