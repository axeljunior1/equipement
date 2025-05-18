package com.projet.equipement.services;


import com.projet.equipement.dto.role.RoleGetDto;
import com.projet.equipement.dto.role.RolePostDto;
import com.projet.equipement.dto.role.RoleUpdateDto;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.RoleMapper;
import com.projet.equipement.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthorityService authorityService;
    private final RoleMapper roleMapper;
    private final AuthorityRoleService authorityRoleService;
    private final EntityManager entityManager;

    public RoleService(RoleRepository roleRepository,
                       AuthorityService authorityService,
                       RoleMapper roleMapper,
                       EntityManager entityManager,
                       AuthorityRoleService authorityRoleService) {
        this.roleRepository = roleRepository;
        this.authorityService = authorityService;
        this.roleMapper = roleMapper;
        this.authorityRoleService = authorityRoleService;
        this.entityManager = entityManager;
    }

    /**
     * Retrieves a paginated list of all roles sorted by the default property "nom"
     * in ascending order unless specified otherwise.
     *
     * @param pageable the pagination and sorting information, including page size, number, and sort order
     * @return a paginated list of roles
     */
    public Page<Role> findAll(@PageableDefault(sort = "nom", direction = Sort.Direction.ASC) Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


    /**
     * Retrieves a Role entity by its unique identifier.
     *
     * @param id the unique identifier of the Role to retrieve
     * @return the Role entity if found
     * @throws EntityNotFoundException if no Role is found with the given identifier
     */
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));
    }

    public Role findByNom(String  nom) {
        return roleRepository.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Role", String.valueOf(nom)));
    }


    @Transactional
    public Role save(RolePostDto rolePostDto) {
// 1. Mapper et encoder
        Role role = roleMapper.toEntity(rolePostDto);

        String tenantId = TenantContext.getTenantId();

        role.setTenantId(tenantId);

// 2. Sauver pour obtenir l'ID
        Role save = roleRepository.save(role);

        authorityRoleService.save(tenantId, role, rolePostDto.getAutoritiesId());

        return roleRepository.save(save);
    }


    public Role save(Role role) {
        role.setTenantId(TenantContext.getTenantId());
        return roleRepository.save(role);
    }



    @Transactional
    public RoleGetDto update(RoleUpdateDto roleUpdateDto, Long id) {

        // 1. Récupérer l'employé
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role", id));

        roleMapper.updateRoleFromDto(roleUpdateDto, role);

        Role saved = roleRepository.save(role);

        // 4. Gérer les rôles
        authorityRoleService.deleteByRoleId(id);

        entityManager.clear();
        authorityRoleService.save(TenantContext.getTenantId(), role, roleUpdateDto.getAuthoritiesIds());

        // 5. Retourner le DTO
        return roleMapper.toGetDto(saved);
    }




    @Transactional
    public void deleteById(Long id) {

        roleRepository.deleteById(id);
    }




}
