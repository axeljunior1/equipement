package com.projet.equipement.services;


import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.AuthorityRepository;
import com.projet.equipement.repository.AuthorityRoleRepository;
import com.projet.equipement.repository.RoleRepository;
import com.projet.equipement.repository.TenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthorityRoleService {
    private final AuthorityRoleRepository authorityRoleRepository;
    private final TenantRepository tenantRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    public AuthorityRoleService(AuthorityRoleRepository authorityRoleRepository,
                                TenantRepository tenantRepository,
                                AuthorityRepository authorityRepository, RoleRepository roleRepository) {
        this.authorityRoleRepository = authorityRoleRepository;
        this.tenantRepository = tenantRepository;
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
    }

    public Page<AuthorityRole> findAll(Pageable pageable) {
        return authorityRoleRepository.findAll(pageable);
    }

    public AuthorityRole findById(Long id) {
        return authorityRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuthorityRole", id));
    }


    public Page<AuthorityRole> findByRoleId(Long id, Pageable pageable) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role", id));
        return authorityRoleRepository.findByRole(role, pageable);
    }



    public AuthorityRole save(AuthorityRole authorityRole) {
        return authorityRoleRepository.save(authorityRole);
    }

    public void save(String tenantId, Role role1, Set<Long> AuthIds) {
        if (AuthIds == null || AuthIds.isEmpty()) return;

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Tenant", tenantId));

        List<AuthorityRole> toSave = new ArrayList<>();

        for (Long authId : AuthIds) {
            Authority auth = authorityRepository.findById(authId)
                    .orElseThrow(() -> new EntityNotFoundException("Authority", authId));

            AuthorityRoleId id = new AuthorityRoleId(auth.getId(), role1.getId(), tenantId);

            AuthorityRole re = new AuthorityRole();
            re.setId(id);
            re.setAuthority(auth);
            re.setRole(role1);
            re.setTenant(tenant);

            toSave.add(re);
        }

        authorityRoleRepository.saveAll(toSave); // batch insert
    }



    public void deleteByRoleId( Long roleId) {
        String tenantId = TenantContext.getTenantId();
        authorityRoleRepository.deleteByTenantAndRoleId(tenantId,roleId);
    }






}
