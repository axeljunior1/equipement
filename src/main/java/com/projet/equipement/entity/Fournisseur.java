package com.projet.equipement.entity;

import jakarta.persistence.*;
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
    @GeneratedValue
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "ADRESSE")
    private String adresse;
}

