package com.projet.equipement.controller;

import com.projet.equipement.dto.formatVente.FormatVenteGetDto;
import com.projet.equipement.dto.formatVente.FormatVentePostDto;
import com.projet.equipement.mapper.FormatVenteMapper;
import com.projet.equipement.services.FormatVenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formats-vente")
@RequiredArgsConstructor
public class FormatVenteController {
    
    private final FormatVenteService formatVenteService;
    private final FormatVenteMapper formatVenteMapper;
    
    @GetMapping
    public ResponseEntity<Page<FormatVenteGetDto>> getAllFormatVentes(Pageable pageable) {

        Page<FormatVenteGetDto> collect = formatVenteService.findAll(pageable).map(formatVenteMapper::toDto);

        return ResponseEntity.ok(collect);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FormatVenteGetDto> getFormatVente(@PathVariable Long id) {
        return ResponseEntity.ok(
            formatVenteMapper.toDto(formatVenteService.findById(id))
        );
    }

    @GetMapping("/produit/{id}")
    public ResponseEntity<Page<FormatVenteGetDto>> getAllByProduitId(@PathVariable Long id, Pageable pageable) {
        Page<FormatVenteGetDto> collect = formatVenteService.findAllByProduitId(id, pageable).map(formatVenteMapper::toDto);
        return ResponseEntity.ok(collect);
    }
    
    @PostMapping
    public ResponseEntity<FormatVenteGetDto> createFormatVente(@Valid @RequestBody FormatVentePostDto formatVentePostDto) {
        return ResponseEntity.ok(
                formatVenteService.save(
                    (formatVentePostDto)
                )

        );
    }
}