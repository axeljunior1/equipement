package com.projet.equipement.controller;

import com.projet.equipement.dto.role.achat.RolePostDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Role;
import com.projet.equipement.mapper.RoleMapper;
import com.projet.equipement.repository.RoleRepository;
import com.projet.equipement.services.AuthorityService;
import com.projet.equipement.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/roles")
@RestController
public class RoleController {

    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public RoleController(RoleService roleService, AuthorityService authorityService, RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.authorityService = authorityService;
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> findAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }


    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody RolePostDto rolePostDto) {
        Role role = roleService.save(rolePostDto);
        return ResponseEntity.ok(role);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted");
    }


}
