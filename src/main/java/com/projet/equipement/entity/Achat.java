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
@Table(name = "achats")
public class Achat extends MultiTenantEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_achat")
    private Long id;

    @Column(name = "montant_total" , nullable = false)
    private Double montantTotal;

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "actif", nullable = false)
    private Boolean actif = true;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime dateCreation =LocalDateTime.now();

    @ManyToOne()
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achat")
    private Set<LigneAchat> ligneAchats;


}
