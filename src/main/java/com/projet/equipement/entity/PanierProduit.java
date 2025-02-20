package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "panier_produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanierProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prix_vente")
    private Double prixVente;

    @ManyToOne
    @JoinColumn(name = "id_panier", nullable = false)
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    private Integer quantite;
}
