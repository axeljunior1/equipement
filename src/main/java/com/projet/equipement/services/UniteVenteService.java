package com.projet.equipement.services;

import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.entity.UniteVente;
import com.projet.equipement.repository.UniteVenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UniteVenteService {
    
    private final UniteVenteRepository uniteVenteRepository;
    
    public Page<UniteVente> findAll(Pageable pageable) {
        return uniteVenteRepository.findAll(pageable);
    }
    
    public UniteVente findById(Long id) {
        return uniteVenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unité de vente non trouvée"));
    }
    
    public UniteVente save(UniteVente uniteVente) {
        uniteVente.setTenantId(TenantContext.getTenantId());
        return uniteVenteRepository.save(uniteVente);
    }

    public void deleteById(Long id) {
        uniteVenteRepository.deleteById(id);
    }
}