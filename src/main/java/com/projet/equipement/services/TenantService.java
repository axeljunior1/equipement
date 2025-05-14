package com.projet.equipement.services;

import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.RoleEmployeRepository;
import com.projet.equipement.repository.TenantRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service("tenantService")
public class TenantService {

    private final TenantRepository tenantRepository;
    private final RoleService roleService;
    private final RoleEmployeRepository roleEmployeRepository;
    private final EmployeRepository employeRepository;
    private final EmployeService employeService;
    private final TenantInitializer tenantInitializer;
    private final EntityManager entityManager;

    @Autowired
    public TenantService(TenantRepository tenantRepository,
                         RoleService roleService,
                         EntityManager entityManager,
                         RoleEmployeRepository roleEmployeRepository, EmployeRepository employeRepository, EmployeService employeService, TenantInitializer tenantInitializer) {
        this.tenantRepository = tenantRepository;
        this.roleService = roleService;
        this.roleEmployeRepository = roleEmployeRepository;
        this.employeRepository = employeRepository;
        this.employeService = employeService;
        this.tenantInitializer = tenantInitializer;
        this.entityManager = entityManager;
    }

    // Créer un nouveau tenant
    @Transactional
    public Tenant createTenant(Tenant tenant) throws IOException {
        //Create Tenant
        tenant.setActive(true);
        Tenant savedTenant = tenantRepository.save(tenant);

        //Create Role

        Employe employe = Employe.builder()
                .actif(true)
                .nom(tenant.getId())
                .prenom(tenant.getId())
                .password(tenant.getId())
                .build();
        employe.setTenantId( savedTenant.getId() );
        employe.setTenant( savedTenant );
        employeService.save(employe, tenant.getId());

        entityManager.flush();

        tenantInitializer.initializeTenant(tenant.getId());

        return savedTenant;
    }

    // Récupérer un tenant par son ID
    public Tenant getTenantById(String tenantId) {
        return tenantRepository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Tenant", tenantId));
    }

    // Vérifier si un tenant est actif
    public boolean isTenantActive(String tenantId) {
        return tenantRepository.existsByIdAndIsActiveTrue(tenantId);
    }

    // Mettre à jour les informations d'un tenant
    public Tenant updateTenant(Tenant tenant) {
        if (!tenantRepository.existsById(tenant.getId())) {
            throw new IllegalArgumentException("Tenant non trouvé");
        }
        return tenantRepository.save(tenant);
    }

    // Désactiver un tenant
    public void deactivateTenant(String tenantId) {
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);
        tenantOptional.ifPresent(tenant -> {
            tenant.setActive(false);
            tenantRepository.save(tenant);
        });
    }

    public Page<Tenant> getAll(Pageable pageable) {
        return tenantRepository.findAll(pageable);
    }

    public boolean getIsMasterTenant() {
        return Objects.equals(TenantContext.getTenantId(), "AxelairCorp");
    }
}
