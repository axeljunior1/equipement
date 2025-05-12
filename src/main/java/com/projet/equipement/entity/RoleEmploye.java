package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_employe")
public class RoleEmploye  {

    @EmbeddedId
    private EmployeeRoleId id;

    @MapsId("employeeId")
    @ManyToOne
    @JoinColumn(name = "id_employe") // <- ici
    private Employe employe;

    @MapsId("roleId")
    @ManyToOne
    @JoinColumn(name = "id_role") // <- ici
    private Role role;

    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id")
    @ManyToOne
    private Tenant tenant;

    // getters/setters…
}

