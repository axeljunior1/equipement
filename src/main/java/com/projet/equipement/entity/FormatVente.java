package com.projet.equipement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "format_vente")
public class FormatVente extends MultiTenantEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    @NotNull(message = "Le produit est obligatoire")
    private Produit produit;
    
    @ManyToOne
    @JoinColumn(name = "unite_vente_id", nullable = false)
    @NotNull(message = "L'unité de vente est obligatoire")
    private UniteVente uniteVente;
    
    @NotBlank(message = "Le libellé du format ne peut pas être vide")
    @Column(name = "libelle_format", nullable = false)
    private String libelleFormat;
    
    @Positive(message = "La quantité par format doit être positive")
    @Column(name = "quantite_par_format", nullable = false)
    private int quantiteParFormat;
    
    @NotNull(message = "Le prix de vente est obligatoire")
    @Positive(message = "Le prix de vente doit être positif")
    @Column(name = "prix_vente", nullable = false)
    private BigDecimal prixVente;
}