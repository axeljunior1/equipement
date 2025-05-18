package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_authority")
public class AuthorityRole {

    @EmbeddedId
    private AuthorityRoleId id;

    @MapsId("authorityId")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_authority") // <- ici
    private Authority authority;

    @MapsId("roleId")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_role") // <- ici
    private Role role;

    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id")
    @JsonIgnore
    @ManyToOne
    private Tenant tenant;

    // getters/setters…
}

