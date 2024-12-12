package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SORTIES")
public class Sortie {
    @GeneratedValue
    @Id
    @Column(name = "ID_SORTIE")
    private Long id;
    @Column(name = "QUANTITE")
    private int quantite;

    @Column(name = "DATE_SORTIE")
    private String dateSortie;

    @Column(name = "PRIX_VENTE")
    private double prixVente;

    @OneToOne
    @JoinColumn(name = "ID_PRODUIT")
    private Produit produit;

    @OneToOne
    @JoinColumn(name = "ID_UTILISATEUR")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "ID_CLIENT")
    private Client client;
}

