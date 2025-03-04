package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etat_vente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtatVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle;

    private String description;
}
