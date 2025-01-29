package com.projet.equipement.services;


import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.VenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenteService {
    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;
    private final EmployeService employeService;
    private final ClientService clientService;
    private final LigneVenteRepository ligneVenteRepository;

    public VenteService(VenteRepository venteRepository, VenteMapper venteMapper, EmployeService employeService, ClientService clientService, LigneVenteRepository ligneVenteRepository) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
        this.employeService = employeService;
        this.clientService = clientService;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    public Page<Vente> findAll(Pageable pageable) {
        return venteRepository.findAll(pageable);
    }
    public Page<LigneVente> findLineByVenteId(Long id, Pageable pageable) {
        return ligneVenteRepository.findLineByVenteId(id, pageable);
    }

    public Vente findById(Long id) {
        return venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vente", id));
    }

    public Vente save(VentePostDto ventePostDto) {
//        Set<Role> roles = vente.getRoles();
       Vente vente =  venteMapper.postVenteDto(ventePostDto, clientService, employeService);

        return venteRepository.save(vente);
    }

    public void deleteById(Long id) {
        venteRepository.deleteById(id);
    }



    public Vente updateVente(VenteUpdateDto venteUpdateDto, Long id) {
        Vente vente = findById(id);
//        Set<Role> roles = new HashSet<>();
        venteMapper.updateVenteFromDto(venteUpdateDto,vente, clientService, employeService);
        return venteRepository.save(vente);
    }

    // Add a role to a user
//    public void addRoleToUser(Long userId, Long roleId) {
//        Vente vente = findById(userId);
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        // Add the role to the user
//        vente.getRoles().add(role);
//        venteRepository.save(vente);
//    }

}
