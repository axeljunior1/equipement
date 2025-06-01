package com.projet.equipement.controller;

import com.projet.equipement.dto.role.RoleGetDto;
import com.projet.equipement.dto.role.RolePostDto;
import com.projet.equipement.dto.role.RoleUpdateDto;
import com.projet.equipement.entity.PaiementRequest;
import com.projet.equipement.entity.Role;
import com.projet.equipement.mapper.RoleMapper;
import com.projet.equipement.services.MtnMomoService;
import com.projet.equipement.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/roles")
@RestController
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<Role>> findAllRoles(@PageableDefault(sort = "nom", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Role> roles = roleService.findAll(pageable);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleGetDto> findRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(roleMapper.toGetDto(role));
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<RoleGetDto> findRoleByNom(@PathVariable String nom) {
        Role role = roleService.findByNom(nom);
        return ResponseEntity.ok(roleMapper.toGetDto(role));
    }


    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody RolePostDto rolePostDto) {
        Role role = roleService.save(rolePostDto);
        return ResponseEntity.ok(role);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleGetDto> updateRole(@RequestBody RoleUpdateDto roleUpdateDto, @PathVariable Long id) {
        RoleGetDto roleGetDto =  roleService.update( roleUpdateDto, id);
        return ResponseEntity.ok(roleGetDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted");
    }


}

