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
@Table(name = "tenant")
public class Tenant {

    @Id
    private String id; // Clé primaire : 'client1', 'client2', etc.

    private String name; // Nom du tenant
    @Column(name = "is_active")
    private boolean isActive; // Statut actif du tenant
    @Column(name = "created_at")
    private LocalDateTime createdAt; // Date de création



}

