package com.projet.equipement.controller;

import com.projet.equipement.entity.Authority;
import com.projet.equipement.services.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/autorisations")
@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("")
    public ResponseEntity<List<Authority>> findAllAuthorities() {
        List<Authority> authorities = authorityService.findAll();
        return ResponseEntity.ok( authorities) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Authority> findAuthority(@PathVariable Long id) {
         Authority authority = authorityService.findById(id);
        return ResponseEntity.ok(authority);
    }



    @PostMapping("")
    public ResponseEntity<Authority> addAuthority(@RequestBody Authority authority) {
       Authority authority1 = authorityService.save(authority);
        return ResponseEntity.ok(authority1);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthority(@PathVariable Long id ) {
        authorityService.deleteById(id);
        return ResponseEntity.ok("Authority deleted");
    }



}
