package com.projet.equipement.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AuthorityRoleId extends MultiTenantEntity implements Serializable {
    private Long authorityId;
    private Long roleId;
    private String tenantId;
    // equals/hashCode…
}
