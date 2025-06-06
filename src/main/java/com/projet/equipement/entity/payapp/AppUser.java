package com.projet.equipement.entity.payapp;

import com.projet.equipement.entity.MultiTenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APP_USER")
public class AppUser extends MultiTenantEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role;
}


