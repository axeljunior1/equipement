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
@Table(name = "mouvement_stock")
public class MouvementStock extends MultiTenantEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique

    @Column(name = "reference")
    private String reference; // Référence unique du mouvement

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit; // Référence vers le produit (relation Many-to-One)

    @Column(name = "quantite", nullable = false)
    private Integer quantite; // Quantité du mouvement (doit être positive)

    @Column(name = "id_ligne_origine", nullable = false)
    private Integer idLigneOrigine;

    @Column(name = "id_evenement_origine", nullable = false)
    private Integer idEvenementOrigine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_mouvement_id", nullable = false)
    private TypeMouvementStock typeMouvement; // Relation Many-to-One vers types_mouvement_stock

    @Column(name = "date_mouvement")
    @Builder.Default
    private LocalDateTime dateMouvement = LocalDateTime.now(); // Date du mouvement (par défaut : maintenant)

    @Column(name = "commentaire")
    private String commentaire; // Commentaire facultatif

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // Date de création

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now(); // Date de mise à jour

}

