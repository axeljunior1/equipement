package com.projet.equipement.controller;

import com.projet.equipement.entity.AuthorityRole;
import com.projet.equipement.services.AuthorityRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/autorisations-role")
@RestController
public class AuthorityRoleController {

    private final AuthorityRoleService authorityRoleService;

    public AuthorityRoleController(AuthorityRoleService authorityRoleService) {
        this.authorityRoleService = authorityRoleService;
    }

    @GetMapping("")
    public ResponseEntity<Page<AuthorityRole>> findAllAuthRoles(Pageable pageable) {
        Page<AuthorityRole> authorityRoles = authorityRoleService.findAll(pageable);
        return ResponseEntity.ok( authorityRoles) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity< AuthorityRole> findAuthByRole(@PathVariable Long id) {
        AuthorityRole authorityRole = authorityRoleService.findById(id);
        return ResponseEntity.ok(authorityRole);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity< Page<AuthorityRole>> findAuthByRole(@PathVariable Long id, Pageable pageable) {
        Page <AuthorityRole> authorityRoles = authorityRoleService.findByRoleId(id, pageable);
        return ResponseEntity.ok(authorityRoles);
    }




    @PostMapping("")
    public ResponseEntity<AuthorityRole> addRoleEmploye(@RequestBody AuthorityRole authorityRole1) {
       AuthorityRole authorityRole = authorityRoleService.save(authorityRole1);
        return ResponseEntity.ok(authorityRole);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleEmploye(@PathVariable Long id ) {
        authorityRoleService.deleteByRoleId(id);
        return ResponseEntity.ok("RoleEmploye deleted");
    }



}
