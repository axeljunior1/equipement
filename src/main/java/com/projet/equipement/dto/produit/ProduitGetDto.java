package com.projet.equipement.dto.produit;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.StockCourant;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitGetDto {

    private Long id;
    private String nom;
    private String description;
    private String image;
    @NotNull(message = "La quantité est obligatoire")
    private Integer quantity;
    private Double prixUnitaire;
    private boolean actif;
    private LocalDateTime created_at ;

    private Integer categorieId;
    private String categorieNom;

    private Integer stockInitial;
    // mis en place dans le controlleur
    private Integer stockCourant;
    private byte[]  qrCode;
    private String  ean13;


    public ProduitGetDto(Produit produit) {
        this.id = produit.getId();
        this.nom = produit.getNom();
        this.description = produit.getDescription();
        this.image = produit.getImage();
        this.prixUnitaire = produit.getPrixUnitaire();
        this.stockInitial = produit.getStockInitial();
        this.ean13 = produit.getEan13();
        this.qrCode = produit.getQrCode();
        this.actif = produit.isActif();
        this.created_at = produit.getCreated_at();
        if (produit.getCategorie() != null) {
            this.categorieId = Math.toIntExact(produit.getCategorie().getId());
            this.categorieNom = produit.getCategorie().getNom();
        }
    }

    public ProduitGetDto(Produit produit, Integer stockCourant) {
        new ProduitGetDto(produit);
        if (stockCourant != null) {
            this.stockCourant = stockCourant;
        }
    }

}
