package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String description;

    private String categorie;

    @Column(name = "qr_code", columnDefinition="bytea")
    private byte[] qrCode;

    @JsonIgnore
    private String image;
    @Column(name = "prix_achat")
    private Integer prixUnitaire;

    @Column(name = "stock_initial")
    private Integer stockInitial;

    @OneToMany(mappedBy ="produit" )
    @JsonIgnore
    private List<LigneVente> ligneVentes;

    @OneToOne(mappedBy ="produit" )
    @JsonIgnore
    private LigneAchat ligneAchat;


//    @OneToMany(mappedBy = "produit")
//    @JsonIgnore
//    private Set<MouvementStock> mouvementStocks ;
//
//    @OneToOne(mappedBy = "produit")
//    @JsonIgnore
//    private LigneVente ligneVente ;

}
