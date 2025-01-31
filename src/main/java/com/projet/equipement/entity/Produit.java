package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projet.equipement.utils.EAN13Generator;
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

    private static String EAN_CONST = new EAN13Generator().generateEAN13WithFirstThreeChars("999");

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

    @Builder.Default
    @Column(name = "qr_code", columnDefinition="bytea")
    private byte[] qrCode = new EAN13Generator().genAndSaveQrCodeByProduct(EAN_CONST);

    @Builder.Default
    @Column(name = "ean13")
    private String ean13 = EAN_CONST;

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
