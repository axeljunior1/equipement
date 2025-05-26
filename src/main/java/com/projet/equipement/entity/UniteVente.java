package com.projet.equipement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unite_vente")
public class UniteVente extends MultiTenantEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le code ne peut pas être vide")
    @Column(nullable = false, unique = true)
    private String code;
    
    @NotBlank(message = "Le libellé ne peut pas être vide")
    @Column(nullable = false)
    private String libelle;
}