package com.projet.equipement.controller;

import com.projet.equipement.entity.Role;
import com.projet.equipement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> findRoleById(@PathVariable int id) {
        Optional<Role> role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }
    @GetMapping
    public ResponseEntity<List<Role>> findAllRole() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.ok(role);
    }
    @PutMapping
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.ok(role);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted");
    }

}
