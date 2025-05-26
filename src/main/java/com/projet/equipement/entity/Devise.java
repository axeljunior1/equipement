package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devise")
public class Devise extends MultiTenantEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false, unique = true)
    private String code;     // Ex: USD, EUR

    @Column(length = 50, nullable = false)
    private String nom;      // Ex: Dollar américain

    @Column(length = 5)
    private String symbole;  // Ex: $


}

