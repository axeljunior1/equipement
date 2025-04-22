package com.projet.equipement.controller;

import com.projet.equipement.dto.role.achat.RoleGetDto;
import com.projet.equipement.dto.role.achat.RolePostDto;
import com.projet.equipement.dto.role.achat.RoleUpdateDto;
import com.projet.equipement.entity.Role;
import com.projet.equipement.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Role>> findAllRoles(@PageableDefault(sort = "nom", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Role> roles = roleService.findAll(pageable);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleGetDto> findRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(new RoleGetDto(role));
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<RoleGetDto> findRoleByNom(@PathVariable String nom) {
        Role role = roleService.findByNom(nom);
        return ResponseEntity.ok(new RoleGetDto(role));
    }


    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody RolePostDto rolePostDto) {
        Role role = roleService.save(rolePostDto);
        return ResponseEntity.ok(role);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Role> updateRole(@RequestBody RoleUpdateDto roleUpdateDto, @PathVariable Long id) {
        return ResponseEntity.ok(new Role());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRolePut(@RequestBody RoleUpdateDto roleUpdateDto, @PathVariable Long id) {
        roleService.put(id, roleUpdateDto);
        return ResponseEntity.ok(new Role());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted");
    }


}
