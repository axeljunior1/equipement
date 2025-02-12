package com.projet.equipement.security;



import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.services.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
    private final EmployeService employeService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmployeService employeService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeService = employeService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok().body( new JwtResponse(token, employeService.findByUsername(loginRequest.getUsername())));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Nom ou mot de passe incorrect. =>"+ jwtUtil.generateToken(loginRequest.getUsername()));
        }
    }




}
