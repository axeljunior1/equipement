package com.projet.equipement.services;


import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.MouvementStockRepository;
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

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    public Page<MouvementStock> findAll(Pageable pageable){
        return mouvementStockRepository.findAll(pageable);
    }



    public  MouvementStock findById(Long id){
        return  mouvementStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }

    public  MouvementStock findByReference(String reference){
        return  mouvementStockRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("Produit", reference));
    }



    public void enregistrerMouvementStock(Long produitId, int quantite, String reference, String commentaire, String typeMouvementCode, Integer idEveOrigine, Integer idLigneOrigine) {
        LocalDateTime dateCreate = LocalDateTime.now();
        MouvementStockPostDto mouvementStockPostDto = MouvementStockPostDto.builder()
                .reference(reference)
                .produitId(produitId)
                .quantite(quantite)
                .commentaire(commentaire)
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode(typeMouvementCode)
                .idEvenementOrigine(idEveOrigine)
                .idLigneOrigine(idLigneOrigine)
                .build();

        MouvementStock entity = mouvementStockMapper.PostMouvementStockFromDto(mouvementStockPostDto);
        entity.setTenantId(TenantContext.getTenantId());
        mouvementStockRepository.save(entity);
    }


    public MouvementStock save(MouvementStockPostDto mouvementStockPostDto){
        MouvementStock mouvementStock = mouvementStockMapper.PostMouvementStockFromDto(mouvementStockPostDto);
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


    public void deleteByIdOrigineEveAndTypeMvtCode(Long idOrigineEve, String code) {
        mouvementStockRepository.deleteByIdEvenementOrigineAndTypeMouvementCode(idOrigineEve, code);
    }

    public List<MouvementStock> findByIdEvenementOrigineAndTypeMouvementCode(Long idOrigineEve, String code) {
       return mouvementStockRepository.findByIdEvenementOrigineAndTypeMouvementCode(idOrigineEve, code);
    }

    @Transactional
    public List<MouvementStock> updateMassMouvementStock(Map<Long, MouvementStockUpdateDto> updates) {
        // Récupérer tous les MouvementStock concernés en une seule requête
        List<MouvementStock> mouvements = mouvementStockRepository.findAllById(updates.keySet());

        // Mettre à jour chaque MouvementStock
        mouvements.forEach(mouvementStock -> {
            MouvementStockUpdateDto dto = updates.get(mouvementStock.getId());
            if (dto != null) {
                mouvementStock.setTenantId(TenantContext.getTenantId());
                mouvementStockMapper.updateMouvementStockFromDto(dto, mouvementStock);
            }
        });

        // Sauvegarder tous les mouvements mis à jour en une seule requête
        return mouvementStockRepository.saveAll(mouvements);
    }




}
