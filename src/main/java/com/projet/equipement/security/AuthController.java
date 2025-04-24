package com.projet.equipement.security;


import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.panier.PanierPostDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.Panier;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.services.EmployeService;
import com.projet.equipement.services.PanierService;
import com.projet.equipement.services.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
    private final EmployeService employeService;
    private final ProduitService produitService;
    private final ProduitMapper produitMapper;
    private final PanierService panierService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmployeService employeService, ProduitService produitService, ProduitMapper produitMapper, PanierService panierService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeService = employeService;
        this.produitService = produitService;
        this.produitMapper = produitMapper;
        this.panierService = panierService;
    }

    @GetMapping
    public ResponseEntity<Page<ProduitGetDto>> getEmployee(Pageable pageable) {
        return ResponseEntity.ok(produitService.findAll(pageable).map(produitMapper::toGetDto));
    }

    @GetMapping("/test")
    public String test() {
        return "Mengue";
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            EmployeGetDto byUsername = employeService.findByUsername(loginRequest.getUsername());


            List<Panier> panier = panierService.findAllByEmployeId(byUsername.getId());

            if (panier.isEmpty()) {
                PanierPostDto genPanier =
                        new PanierPostDto();

                genPanier.setEmployeId(byUsername.getId());
                genPanier.setEtatId(1L);

                panierService.save(genPanier);
            }

            panier = panierService.findAllByEmployeId(byUsername.getId());

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .employeGetDto(byUsername)
                    .panier(panier)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Nom ou mot de passe incorrect. =>" + jwtUtil.generateToken(loginRequest.getUsername()));
        }
    }


}
