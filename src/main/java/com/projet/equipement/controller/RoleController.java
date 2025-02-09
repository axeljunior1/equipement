package com.projet.equipement.controller;

import com.projet.equipement.entity.Role;
import com.projet.equipement.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> findAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok( roles) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Role> findRole(@PathVariable Long id) {
         Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }



    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
       Role role1 = roleService.save(role);
        return ResponseEntity.ok(role1);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id ) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted");
    }



}
