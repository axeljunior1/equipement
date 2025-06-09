package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etat_paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtatPaiement extends MultiTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle;

    private String description;
}
