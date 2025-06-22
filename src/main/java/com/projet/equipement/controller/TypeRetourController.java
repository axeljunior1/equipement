package com.projet.equipement.controller;

import com.projet.equipement.entity.TypeRetour;
import com.projet.equipement.services.TypeRetourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/type-retours")
public class TypeRetourController {

    private final TypeRetourService typeRetourService;

    public TypeRetourController(TypeRetourService typeRetourService) {
        this.typeRetourService = typeRetourService;
    }

    @GetMapping
    public Page<TypeRetour> findAll(Pageable pageable) {
        return typeRetourService.findAll(pageable);
    }
}
