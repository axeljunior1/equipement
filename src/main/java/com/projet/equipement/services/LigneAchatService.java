package com.projet.equipement.services;


import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.LigneAchat;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class LigneAchatService {

    private final LigneAchatRepository ligneAchatRepository;
    private final LigneAchatMapper ligneAchatMapper;
    private final AchatService achatService;
    private final ProduitService produitService;
    private final MouvementStockService mouvementStockService;

    public LigneAchatService(LigneAchatRepository ligneAchatRepository, LigneAchatMapper ligneAchatMapper, AchatService achatService, ProduitService produitService, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, ProduitRepository produitRepository, TypeMouvementStockRepository typeMouvementStockRepository, MouvementStockService mouvementStockService) {
        this.ligneAchatRepository = ligneAchatRepository;
        this.ligneAchatMapper = ligneAchatMapper;
        this.achatService = achatService;
        this.produitService = produitService;
        this.mouvementStockService = mouvementStockService;
    }

    public Page<LigneAchat> findAll(Pageable pageable){
        return ligneAchatRepository.findAll(pageable);
    }

    public  LigneAchat findById(Long id){
        return  ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id));
    }

    @Transactional
    public LigneAchat save(LigneAchatPostDto ligneAchatPostDto){

        LigneAchat ligneAchat = ligneAchatMapper.postLigneAchatFromDto(ligneAchatPostDto, achatService, produitService);
        LigneAchat saveLigneAchat = ligneAchatRepository.save(ligneAchat);

        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.enregistrerMouvementStock(
                Long.valueOf(ligneAchatPostDto.getProduitId()),
                ligneAchatPostDto.getQuantite(),
                "ACH_"+ ligneAchatPostDto.getAchatId() + "_LIG_"+ saveLigneAchat.getId(),
                "Généré à partir de la ligne d'un achat",
                "ACHAT_MARCHANDISE",
                Math.toIntExact(ligneAchat.getAchat().getId()),
                Math.toIntExact(ligneAchat.getId())
        );


        return saveLigneAchat;
    }


    public LigneAchat updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id){
        LigneAchat ligneAchat = findById(id);
        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat, achatService, produitService);
        return ligneAchatRepository.save(ligneAchat);
    }

    @Transactional
    public void deleteById(Long id){
        // cet id est l'id de la ligne d'achat
        LigneAchat ligneAchat = this.findById(id);
        String reference = "ACH_"+ ligneAchat.getAchat().getId() + "_LIG_"+ ligneAchat.getId() ;

        mouvementStockService.deleteByReference(reference);
        ligneAchatRepository.deleteById(id);
    }

    public Page<LigneAchat> findByAchatId(Long id, Pageable pageable) {
        return ligneAchatRepository.findByAchatId(id,pageable);
    }
}
