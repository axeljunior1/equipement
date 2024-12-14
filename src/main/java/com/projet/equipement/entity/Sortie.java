package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sorties")
public class Sortie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sortie")
    private Long id;
    @Column(name = "quantite")
    private int quantite;

    @Column(name = "date_sortie")
    private LocalDateTime dateSortie;

    @Column(name = "prix_vente")
    private double prixVente;

    @OneToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client client;
}

