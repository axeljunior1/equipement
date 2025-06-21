package com.projet.equipement.entity;

public interface TenantAware {
    void setTenantId(String tenantId);
    String getTenantId();
}
