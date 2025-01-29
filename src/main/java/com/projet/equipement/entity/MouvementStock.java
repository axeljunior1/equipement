package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mouvement_stock")
public class MouvementStock  {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_mouvement_id", nullable = false)
    private TypeMouvementStock typeMouvement; // Relation Many-to-One vers types_mouvement_stock

    @Column(name = "date_mouvement")
    private LocalDateTime dateMouvement; // Date du mouvement (par défaut : maintenant)

    @Column(name = "commentaire")
    private String commentaire; // Commentaire facultatif

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Date de création

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Date de mise à jour

}

