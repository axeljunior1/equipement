package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entrees")
public class Entree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entree")
    private Long id_entree;

    private int quantite;

    @Column(name = "prix_achat")
    private double prix_achat;
    @OneToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
    @OneToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseur fournisseur;
}

