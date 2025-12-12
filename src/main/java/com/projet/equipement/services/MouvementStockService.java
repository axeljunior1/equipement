package com.projet.equipement.services;


import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.TypeMouvementStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MouvementStockService {

    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;
    private final ProduitRepository produitRepository;
    private final TypeMouvementStockRepository typeMouvementStockRepository;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository,
                                 MouvementStockMapper mouvementStockMapper, ProduitRepository produitRepository, TypeMouvementStockRepository typeMouvementStockRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.produitRepository = produitRepository;
        this.typeMouvementStockRepository = typeMouvementStockRepository;
    }

    public Page<MouvementStock> findAll(Pageable pageable){
        return mouvementStockRepository.findAll(pageable);
    }


    public  MouvementStock findById(Long id){
        return  mouvementStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }


    public MouvementStock save(MouvementStockPostDto mouvementStockPostDto){
        MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockPostDto);

        mouvementStock.setProduit(produitRepository.findById(mouvementStockPostDto.getProduitId()).orElseThrow(()-> new EntityNotFoundException("Produit", mouvementStockPostDto.getProduitId())));
        mouvementStock.setTypeMouvement(typeMouvementStockRepository.findByCode(mouvementStockPostDto.getTypeMouvementCode()).orElseThrow(()-> new EntityNotFoundException("TypeMouvementStock", mouvementStockPostDto.getTypeMouvementCode())));

        mouvementStock.setTenantId(TenantContext.getTenantId());
        return mouvementStockRepository.save(mouvementStock);
    }


    public MouvementStock updateMouvementStock(MouvementStockUpdateDto mouvementStockUpdateDto, Long id){
        MouvementStock mouvementStock = findById(id);
        mouvementStockMapper.updateMouvementStockFromDto(mouvementStockUpdateDto, mouvementStock);
        return mouvementStockRepository.save(mouvementStock);
    }


    /**
     * Soft delete
     * @param id id du mvt
     */
    public void deleteById(Long id){
        mouvementStockRepository.deleteById(id);
    }

    public Page<MouvementStock> findAllMouvementStockByProductId(Long id, Pageable pageable) {
        return mouvementStockRepository.findAllMouvementStockByProductId(id, pageable) ;
    }






}
