package com.projet.equipement.services;


import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.TypeMouvementStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TypeMouvementStockService {


    private final TypeMouvementStockRepository typeMouvementStockRepository;

    public TypeMouvementStockService(TypeMouvementStockRepository typeMouvementStockRepository) {
        this.typeMouvementStockRepository = typeMouvementStockRepository;
    }

    public Page<TypeMouvementStock> findAll(Pageable pageable) {
        return typeMouvementStockRepository.findAll(pageable);
    }

    public TypeMouvementStock findById(Long id) {
        return typeMouvementStockRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("TypeMouvementStock", id));
    }

    public TypeMouvementStock findByCode(String code) {
        return typeMouvementStockRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("TypeMouvementStock", code));
    }

    public TypeMouvementStock save(TypeMouvementStock entity) {
        entity.setTenantId(TenantContext.getTenantId());
        return typeMouvementStockRepository.save(entity);
    }

    public void delete(TypeMouvementStock entity) {
        typeMouvementStockRepository.delete(entity);
    }



}
