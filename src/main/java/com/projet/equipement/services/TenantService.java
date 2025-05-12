package com.projet.equipement.services;
import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.RoleEmployeRepository;
import com.projet.equipement.repository.RoleRepository;
import com.projet.equipement.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service("tenantService")
public class TenantService {

    private final TenantRepository tenantRepository;
    private final RoleService roleService;
    private final RoleEmployeRepository roleEmployeRepository;
    private final EmployeRepository employeRepository;
    private final EmployeService employeService;

    @Autowired
    public TenantService(TenantRepository tenantRepository,
                         RoleService roleService,
                         RoleEmployeRepository roleEmployeRepository, EmployeRepository employeRepository, EmployeService employeService) {
        this.tenantRepository = tenantRepository;
        this.roleService = roleService;
        this.roleEmployeRepository = roleEmployeRepository;
        this.employeRepository = employeRepository;
        this.employeService = employeService;
    }

    // Créer un nouveau tenant
    @Transactional
    public Tenant createTenant(Tenant tenant) {
        //Create Tenant
        tenant.setActive(true);
        Tenant savedTenant = tenantRepository.save(tenant);
        //Create Role
        Role admin = roleService.findByNom("ADMIN");


        Employe employe = Employe.builder()
                .actif(true)
                .nom(tenant.getId())
                .prenom(tenant.getId())
                .password(tenant.getId())
                .build();
        employe.setTenantId( savedTenant.getId() );
        employe.setTenant( savedTenant );
        Employe savedEmploye  = employeService.save(employe, tenant.getId());

        RoleEmploye roleEmploye = new RoleEmploye();

        roleEmploye.setId(new EmployeeRoleId(savedEmploye.getId(), admin.getId(), savedTenant.getId()));
        roleEmploye.setEmploye(savedEmploye);
        roleEmploye.setRole(admin);
        roleEmploye.setTenant(savedTenant);

        roleEmployeRepository.save(roleEmploye);

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
