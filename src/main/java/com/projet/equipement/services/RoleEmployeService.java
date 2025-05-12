package com.projet.equipement.services;


import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.RoleEmployeRepository;
import com.projet.equipement.repository.RoleRepository;
import com.projet.equipement.repository.TenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoleEmployeService {
    private final RoleEmployeRepository roleEmployeRepository;
    private final EmployeRepository employeRepository;
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;

    public RoleEmployeService(RoleEmployeRepository roleEmployeRepository,
                              EmployeRepository employeRepository,
                              TenantRepository tenantRepository, RoleRepository roleRepository) {
        this.roleEmployeRepository = roleEmployeRepository;
        this.employeRepository = employeRepository;
        this.tenantRepository = tenantRepository;
        this.roleRepository = roleRepository;
    }

    public Page<RoleEmploye> findAll(Pageable pageable) {
        return roleEmployeRepository.findAll(pageable);
    }

    public RoleEmploye findById(Long id) {
        return roleEmployeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoleEmploye", id));
    }



    public RoleEmploye save(RoleEmploye roleEmploye) {
        return roleEmployeRepository.save(roleEmploye);
    }

    public void save(String tenantId, Employe employe, Set<Long> rolesIds) {
        if (rolesIds == null || rolesIds.isEmpty()) return;

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Tenant", tenantId));

        List<RoleEmploye> toSave = new ArrayList<>();

        for (Long roleId : rolesIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role", roleId));

            EmployeeRoleId id = new EmployeeRoleId(employe.getId(), role.getId(), tenantId);

            RoleEmploye re = new RoleEmploye();
            re.setId(id);
            re.setEmploye(employe);
            re.setRole(role);
            re.setTenant(tenant);

            toSave.add(re);
        }

        roleEmployeRepository.saveAll(toSave); // batch insert
    }



    public void deleteByEmployeId( Long employeId) {
        String tenantId = TenantContext.getTenantId();
        roleEmployeRepository.deleteByTenantAndEmployeId(tenantId,employeId);
    }

    public void assignRoleToEmploye(Long employeeId, Long roleId, String tenantId) {
        Employe employe = employeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employe", employeeId));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role", roleId));
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Tenant", tenantId));

        EmployeeRoleId id = new EmployeeRoleId(employeeId, roleId, tenantId);

        RoleEmploye roleEmploye = new RoleEmploye();
        roleEmploye.setId(id);
        roleEmploye.setEmploye(employe);
        roleEmploye.setRole(role);
        roleEmploye.setTenant(tenant);

        roleEmployeRepository.save(roleEmploye);
    }




}
