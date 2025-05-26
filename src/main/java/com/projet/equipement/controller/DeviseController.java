package com.projet.equipement.controller;

import com.projet.equipement.dto.devise.DeviseGetDto;
import com.projet.equipement.dto.devise.DevisePostDto;
import com.projet.equipement.dto.devise.DeviseUpdateDto;
import com.projet.equipement.entity.Devise;
import com.projet.equipement.mapper.DeviseMapper;
import com.projet.equipement.services.DeviseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/devises")
@RestController
public class DeviseController {


    private final DeviseService deviseService;
    private final DeviseMapper deviseMapper;

    public DeviseController(DeviseService deviseService, DeviseMapper deviseMapper) {
        this.deviseService = deviseService;
        this.deviseMapper = deviseMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<DeviseGetDto>> findAllDevises(Pageable pageable) {
        Page<Devise> devises = deviseService.findAll(pageable);
        return ResponseEntity.ok(devises.map(deviseMapper::toDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviseGetDto> findDevise(@PathVariable Long id) {
        Devise devise = deviseService.findById(id);
        return ResponseEntity.ok(deviseMapper.toDto(devise));
    }



    @PostMapping()
    public ResponseEntity<DeviseGetDto> save(@RequestBody DevisePostDto devisePostDto) {
        Devise devise = deviseService.save(devisePostDto);
        return ResponseEntity.ok(deviseMapper.toDto(devise));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<DeviseGetDto> updateDevise(@PathVariable Long id, @Valid @RequestBody DeviseUpdateDto deviseUpdateDto) {

        Devise devise = deviseService.update(deviseUpdateDto, id);
        return ResponseEntity.ok(deviseMapper.toDto(devise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevise(@PathVariable Long id) {
        deviseService.deleteById(id);
        return ResponseEntity.ok("Devise deleted");
    }


}
