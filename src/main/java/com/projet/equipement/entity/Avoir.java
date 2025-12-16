package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "avoir")
public class Avoir extends MultiTenantEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "retour_id")
    private Retour retour;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vente_origine_id")
    private Vente venteOrigine;

    @Column(name = "montant_initial", nullable = false)
    private BigDecimal montantInitial;

    @ManyToOne(optional = false)
    @JoinColumn(name = "etat_id")
    private EtatAvoir etat;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
