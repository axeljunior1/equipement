package com.projet.equipement.services;

import com.projet.equipement.entity.UniteVente;
import com.projet.equipement.repository.UniteVenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UniteVenteService {
    
    private final UniteVenteRepository uniteVenteRepository;
    
    public List<UniteVente> findAll() {
        return uniteVenteRepository.findAll();
    }
    
    public UniteVente findById(Long id) {
        return uniteVenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unité de vente non trouvée"));
    }
    
    public UniteVente save(UniteVente uniteVente) {
        return uniteVenteRepository.save(uniteVente);
    }
}