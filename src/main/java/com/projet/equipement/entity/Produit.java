package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @ManyToOne()
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @Column(name = "qr_code", columnDefinition="bytea")
    private byte[] qrCode;

    @Column(name = "ean13")
    private String ean13 ;

    @Builder.Default
    @Column(name = "actif")
    private boolean actif = true ;

    @Column(name = "created_at")
    private LocalDateTime created_at ;

    @JsonIgnore
    private String image;
    @Column(name = "prix_achat")
    private Double prixUnitaire;

    @Column(name = "stock_initial")
    private Integer stockInitial;

    @OneToMany(mappedBy ="produit" )
    @JsonIgnore
    private List<LigneVente> ligneVentes;

    @OneToMany(mappedBy ="produit" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneAchat> ligneAchats;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<MouvementStock> mouvementStocks;


//    @OneToMany(mappedBy = "produit")
//    @JsonIgnore
//    private Set<MouvementStock> mouvementStocks ;
//
//    @OneToOne(mappedBy = "produit")
//    @JsonIgnore
//    private LigneVente ligneVente ;

}
