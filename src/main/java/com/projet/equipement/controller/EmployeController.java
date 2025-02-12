package com.projet.equipement.controller;

import com.projet.equipement.dto.employe.EmployeGetDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Page<EmployeGetDto>> findAllEmployes(Pageable pageable) {
        Page<EmployeGetDto> employes = employeService.findAll(pageable);
        return ResponseEntity.ok(employes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeGetDto> findEmploye(@PathVariable Long id) {
        EmployeGetDto employe = employeService.findById(id);
        return ResponseEntity.ok(employe);
    }

    @GetMapping("auth")
    public ResponseEntity<Page<Authority>> findEmployeTest(Pageable pageable) {
        Page<Authority> authorities = authorityRepository.findAll(pageable);
        return ResponseEntity.ok(authorities);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<EmployeGetDto> findEmployeByUsername(@PathVariable String username) {
        EmployeGetDto employe = employeService.findByUsername(username);
        return ResponseEntity.ok(employe);
    }

    @PostMapping("")
    public ResponseEntity<Employe> addEmploye(@RequestBody EmployePostDto employePostDto) {
        Employe employe = employeService.save(employePostDto);
        return ResponseEntity.ok(employe);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeGetDto> updateEmploye(@PathVariable Long id, @Valid @RequestBody EmployeUpdateDto employeUpdateDto) {
        EmployeGetDto employe = employeService.updateEmploye(employeUpdateDto, id);
        return ResponseEntity.ok(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeGetDto> updateEmployePut(@PathVariable Long id, @Valid @RequestBody EmployeUpdateDto employeUpdateDto) {
        EmployeGetDto employe = employeService.putEmploye(employeUpdateDto, id);
        return ResponseEntity.ok(employe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable Long id) {
        employeService.deleteById(id);
        return ResponseEntity.ok("Employe deleted");
    }


}
