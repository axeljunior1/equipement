package com.projet.equipement.controller;

import com.projet.equipement.entity.RoleEmploye;
import com.projet.equipement.services.RoleEmployeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/role-employes")
@RestController
public class RoleEmployeController {

    private final RoleEmployeService roleEmployeService;

    public RoleEmployeController(RoleEmployeService roleEmployeService) {
        this.roleEmployeService = roleEmployeService;
    }

    @GetMapping("")
    public ResponseEntity<Page<RoleEmploye>> findAllRolesEmploye(Pageable pageable) {
        Page<RoleEmploye> roleEmployes = roleEmployeService.findAll(pageable);
        return ResponseEntity.ok( roleEmployes) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity< RoleEmploye> findRoleEmploye(@PathVariable Long id) {
         RoleEmploye roleEmploye = roleEmployeService.findById(id);
        return ResponseEntity.ok(roleEmploye);
    }




    @PostMapping("")
    public ResponseEntity<RoleEmploye> addRoleEmploye(@RequestBody RoleEmploye roleEmploye) {
       RoleEmploye roleEmploye1 = roleEmployeService.save(roleEmploye);
        return ResponseEntity.ok(roleEmploye1);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleEmploye(@PathVariable Long id ) {
        roleEmployeService.deleteByEmployeId(id);
        return ResponseEntity.ok("RoleEmploye deleted");
    }



}
