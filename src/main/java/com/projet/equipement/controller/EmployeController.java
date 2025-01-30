package com.projet.equipement.controller;

import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.services.EmployeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employes")
@RestController
public class EmployeController {

    @Autowired
    private EmployeService employeService;

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
