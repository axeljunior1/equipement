package com.projet.equipement.controller;

import com.projet.equipement.dto.uniteVente.UniteVenteGetDto;
import com.projet.equipement.mapper.UniteVenteMapper;
import com.projet.equipement.services.UniteVenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/unites-vente")
@RequiredArgsConstructor
public class UniteVenteController {
    
    private final UniteVenteService uniteVenteService;
    private final UniteVenteMapper uniteVenteMapper;
    
    @GetMapping
    public ResponseEntity<List<UniteVenteGetDto>> getAllUniteVentes() {
        return ResponseEntity.ok(
            uniteVenteService.findAll().stream()
                .map(p->uniteVenteMapper.toDto(p))
                .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UniteVenteGetDto> getUniteVente(@PathVariable Long id) {
        return ResponseEntity.ok(
            uniteVenteMapper.toDto(uniteVenteService.findById(id))
        );
    }
    
    @PostMapping
    public ResponseEntity<UniteVenteGetDto> createUniteVente(@Valid @RequestBody UniteVenteGetDto uniteVenteDto) {
        return ResponseEntity.ok(
            uniteVenteMapper.toDto(
                uniteVenteService.save(
                    uniteVenteMapper.toEntity(uniteVenteDto)
                )
            )
        );
    }
}