package com.projet.equipement.controller;

import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.repository.AuthorityRepository;
import com.projet.equipement.services.EmployeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employes")
@RestController
public class EmployeController {

    private final EmployeService employeService;
    private final AuthorityRepository authorityRepository;

    public EmployeController(EmployeService employeService, AuthorityRepository authorityRepository) {
        this.employeService = employeService;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Employe>> findAllEmployes() {
        List<Employe> employes = employeService.findAll();
        return ResponseEntity.ok( employes) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Employe> findEmploye(@PathVariable Long id) {
         Employe employe = employeService.findById(id);
        return ResponseEntity.ok(employe);
    }
    @GetMapping("auth")
    public ResponseEntity<Page<Authority>> findEmployeTest(Pageable pageable) {
         Page<Authority> authorities = authorityRepository.findAll(pageable);
        return ResponseEntity.ok(authorities);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity< Employe> findEmployeByUsername(@PathVariable String username) {
         Employe employe = employeService.findByUsername(username);
        return ResponseEntity.ok(employe);
    }

    @PostMapping("")
    public ResponseEntity<Employe> addEmploye(@RequestBody EmployePostDto employePostDto) {
       Employe employe = employeService.save(employePostDto);
        return ResponseEntity.ok(employe);
    }
    @PostMapping("auth")
    public ResponseEntity<Authority> addEmploye(@RequestBody Authority authority) {
       Authority authority1 = authorityRepository.save(authority);
        return ResponseEntity.ok(authority1);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id , @Valid  @RequestBody EmployeUpdateDto employeUpdateDto) {
        Employe employe = employeService.updateEmploye(employeUpdateDto, id);
        return ResponseEntity.ok(employe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable Long id ) {
        employeService.deleteById(id);
        return ResponseEntity.ok("Employe deleted");
    }



}
