package com.projet.equipement.controller;

import com.projet.equipement.entity.Tenant;
import com.projet.equipement.services.TenantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tenant")
@RestController
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Tenant>> findAllAuthorities(Pageable pageable) {
        Page<Tenant> authorities = tenantService.getAll(pageable);
        return ResponseEntity.ok( authorities) ;
    }

    @GetMapping("/{id}")
    @PreAuthorize("@tenantService.getIsMasterTenant()==true")
    public ResponseEntity< Tenant> findTenant(@PathVariable String id) {
         Tenant tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenant);
    }



    @PostMapping("")
    @PreAuthorize("@tenantService.getIsMasterTenant()==true")
    public ResponseEntity<Tenant> addTenant(@RequestBody Tenant tenant) {
       Tenant tenant1 = tenantService.createTenant(tenant);
        return ResponseEntity.ok(tenant1);
    }

    

    @DeleteMapping("/{id}")
    @PreAuthorize("@tenantService.getIsMasterTenant()==true")
    public ResponseEntity<String> deleteTenant(@PathVariable String id ) {
        tenantService.deactivateTenant(id);
        return ResponseEntity.ok("Tenant deleted");
    }



}
