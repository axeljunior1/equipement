package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventes")
public class Vente extends MultiTenantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ventes")
    private Long id;

    @Column(name = "montant_total")
    private Double montantTotal;

    @ManyToOne
    @JoinColumn(name = "etat_id", nullable = false)
    private EtatVente etat;

    @Column(name = "actif")
    @Builder.Default
    private Boolean actif = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vente")
    private Set<LigneVente> ligneVentes;

    @OneToMany(mappedBy = "vente")
    private List<Paiements> paiements;

    @OneToMany(mappedBy = "vente")
    private List<Retour> retours;



}

