package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employes")
public class Employe extends MultiTenantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_employe")
    private Long id;
    private String nom;
    private String prenom;
    @JsonIgnore
    private String password;
    @Builder.Default
    private Boolean actif = true;


    @JsonIgnore
    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<RoleEmploye> employeRoles = new HashSet<>();

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime dateCreation =LocalDateTime.now();

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Vente> ventes;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Achat> achats;

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

}


