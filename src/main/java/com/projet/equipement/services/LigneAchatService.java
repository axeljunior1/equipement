package com.projet.equipement.services;


import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneAchatMapper;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.LigneAchatRepository;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.TypeMouvementStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class LigneAchatService {

    private final LigneAchatRepository ligneAchatRepository;
    private final LigneAchatMapper ligneAchatMapper;
    private final AchatService achatService;
    private final ProduitService produitService;
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;
    private final ProduitRepository produitRepository;
    private final TypeMouvementStockRepository typeMouvementStockRepository;

    public LigneAchatService(LigneAchatRepository ligneAchatRepository, LigneAchatMapper ligneAchatMapper, AchatService achatService, ProduitService produitService, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, ProduitRepository produitRepository, TypeMouvementStockRepository typeMouvementStockRepository) {
        this.ligneAchatRepository = ligneAchatRepository;
        this.ligneAchatMapper = ligneAchatMapper;
        this.achatService = achatService;
        this.produitService = produitService;
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.produitRepository = produitRepository;
        this.typeMouvementStockRepository = typeMouvementStockRepository;
    }

    public Page<LigneAchat> findAll(Pageable pageable){
        return ligneAchatRepository.findAll(pageable);
    }

    public  LigneAchat findById(Long id){
        return  ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id));
    }

    public LigneAchat save(LigneAchatPostDto ligneAchatPostDto){


        LigneAchat ligneAchat = ligneAchatMapper.postLigneAchatFromDto(ligneAchatPostDto, achatService, produitService);
        LigneAchat save = ligneAchatRepository.save(ligneAchat);
        // Gestion du mvt de stock
        TypeMouvementStock typeMouvementStock = new TypeMouvementStock();
        LocalDateTime dateCreate = LocalDateTime.now();
        MouvementStockPostDto mouvementStockPostDto = MouvementStockPostDto.builder()
                .reference("ACH_"+ ligneAchatPostDto.getAchatId() + "_LIG_"+ save.getId() )
                .produitId(Long.valueOf(ligneAchatPostDto.getProduitId()))
                .quantite(ligneAchatPostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un achat")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("ACHAT_MARCHANDISE")
                .build();

        // Save un movement de stock
        mouvementStockRepository.save(mouvementStockMapper.PostMouvementStockFromDto(mouvementStockPostDto));

        return save;
    }


    public LigneAchat updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id){
        LigneAchat ligneAchat = findById(id);
        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat, achatService, produitService);
        return ligneAchatRepository.save(ligneAchat);
    }

    public void deleteById(Long id){
        // cet id est l'id de la ligne d'achat
        LigneAchat ligneAchat = findById(id);
        Long idProduit = ligneAchat.getProduit().getId();
        Produit produit = produitRepository.findById(idProduit).orElseThrow(()-> new EntityNotFoundException("Produit", idProduit));
        ligneAchatRepository.deleteById(id);
        TypeMouvementStock typeMouvementStock = typeMouvementStockRepository.findByCode("ACHAT_MARCHANDISE");
        List<MouvementStock> mouvementStocks = mouvementStockRepository.findByProduitAndTypeMouvement(produit, typeMouvementStock);
        for (MouvementStock mouvementStock : mouvementStocks) {
            mouvementStockRepository.deleteById(mouvementStock.getId());
        }
    }

    public Page<LigneAchat> findByAchatId(Long id, Pageable pageable) {
        return ligneAchatRepository.findByAchatId(id,pageable);
    }
}
