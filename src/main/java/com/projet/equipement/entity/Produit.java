package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long id;

    private String nom;
    private String description;
    private String image;
    @Column(name = "prix_unitaire")
    private Integer prixUnitaire;

    @Column(name = "stock_initial")
    private Integer quantity;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private Set<MouvementStock> mouvementStocks;


}
