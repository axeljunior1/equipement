package com.projet.equipement.controller;

import com.projet.equipement.entity.Authority;
import com.projet.equipement.services.AuthorityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/autorisations")
@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Authority>> findAllAuthorities(Pageable pageable) {
        Page<Authority> authorities = authorityService.findAll(pageable);
        return ResponseEntity.ok( authorities) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity< Authority> findAuthority(@PathVariable Long id) {
         Authority authority = authorityService.findById(id);
        return ResponseEntity.ok(authority);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity< Authority> findAuthorityByNom(@PathVariable String nom) {
         Authority authority = authorityService.findByNom(nom);
        return ResponseEntity.ok(authority);
    }



    @PostMapping("")
    public ResponseEntity<Authority> addAuthority(@RequestBody Authority authority) {
       Authority authority1 = authorityService.save(authority);
        return ResponseEntity.ok(authority1);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Authority> updateAuth(@PathVariable Long id , @RequestBody Authority authority ) {
        return ResponseEntity.ok(authorityService.update(authority, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthority(@PathVariable Long id ) {
        authorityService.deleteById(id);
        return ResponseEntity.ok("Authority deleted");
    }



}
