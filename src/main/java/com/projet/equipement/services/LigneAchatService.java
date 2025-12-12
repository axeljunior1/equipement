package com.projet.equipement.services;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.TarifAchat;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneAchatMapper;
import com.projet.equipement.repository.AchatRepository;
import com.projet.equipement.repository.LigneAchatRepository;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class LigneAchatService {

    private final LigneAchatRepository ligneAchatRepository;
    private final MouvementStockService mouvementStockService;
    private final AchatRepository achatRepository;
    private final TarifAchatService tarifAchatService;
    private final LigneAchatMapper ligneAchatMapper;
    private final ProduitRepository produitRepository;

    public LigneAchatService(LigneAchatRepository ligneAchatRepository,
                             MouvementStockService mouvementStockService,
                             AchatRepository achatRepository, TarifAchatService tarifAchatService, LigneAchatMapper ligneAchatMapper, ProduitRepository produitRepository) {
        this.ligneAchatRepository = ligneAchatRepository;
        this.mouvementStockService = mouvementStockService;
        this.achatRepository = achatRepository;
        this.tarifAchatService = tarifAchatService;
        this.ligneAchatMapper = ligneAchatMapper;
        this.produitRepository = produitRepository;
    }

    public Page<LigneAchatGetDto> findAll(Pageable pageable) {
        Page<LigneAchat> allLine = ligneAchatRepository.findAllLine(pageable);
        return allLine.map(ligneAchatMapper::toDto);
    }


    public LigneAchatGetDto findById(Long id) {
        return ligneAchatMapper.toDto(ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id)));
    }

    public Page<LigneAchatGetDto> findByAchatId(Long idAchat, Pageable pageable) {
        Page<LigneAchat> byAchatId = ligneAchatRepository.findByAchatId(idAchat, pageable);
        return byAchatId.map(ligneAchatMapper::toDto);
    }


    @Transactional
    public LigneAchatGetDto save(LigneAchatPostDto ligneAchatPostDto) {

        if (ligneAchatPostDto.getPrixAchatF() != null){
            TarifAchat tarifAchat = tarifAchatService.findByProduitId(ligneAchatPostDto.getProduitId());
            tarifAchat.setPrixAchat(BigDecimal.valueOf(ligneAchatPostDto.getPrixAchatF()));

            tarifAchatService.save(tarifAchat);
        }


        LigneAchat ligneAchat = ligneAchatMapper.toEntity(ligneAchatPostDto);
        //set achat
        ligneAchat.setAchat(achatRepository.findById(ligneAchatPostDto.getAchatId()).orElseThrow(()-> new EntityNotFoundException("Achat", ligneAchatPostDto.getAchatId())));
        //set produit
        ligneAchat.setProduit(produitRepository.findById(ligneAchatPostDto.getProduitId()).orElseThrow(()-> new EntityNotFoundException("Produit", ligneAchatPostDto.getProduitId())));

        ligneAchat.setTenantId(TenantContext.getTenantId());
        LigneAchat saveLigneAchat = ligneAchatRepository.save(ligneAchat);


        updateTotalAchat(saveLigneAchat.getAchat().getId());

        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.save(MouvementStockPostDto.builder()
                .reference("ACH_" + ligneAchatPostDto.getAchatId() + "_LIG_" + saveLigneAchat.getId())
                .produitId(ligneAchatPostDto.getProduitId())
                .quantite(ligneAchatPostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un achat")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("ACHAT_MARCHANDISE")
                .idEvenementOrigine(saveLigneAchat.getAchat().getId())
                .idLigneOrigine(saveLigneAchat.getId())
                .build());


        return ligneAchatMapper.toDto(saveLigneAchat);
    }

    @Transactional
    public LigneAchatGetDto updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id) {
        LigneAchat ligneAchat = ligneAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("LigneAchat", id));

        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat);
        //set achat
        ligneAchat.setAchat(achatRepository.findById(ligneAchatUpdateDto.getAchatId()).orElseThrow(()-> new EntityNotFoundException("Achat", ligneAchatUpdateDto.getAchatId())));
        //set produit
        ligneAchat.setProduit(produitRepository.findById(ligneAchatUpdateDto.getProduitId()).orElseThrow(()-> new EntityNotFoundException("Produit", ligneAchatUpdateDto.getProduitId())));

        ligneAchat.setTenantId(TenantContext.getTenantId());
        LigneAchat savedLine = ligneAchatRepository.save(ligneAchat);
        //mise a jour du total
        updateTotalAchat(savedLine.getAchat().getId());
        return ligneAchatMapper.toDto(savedLine);
    }

    @Transactional
    public void deleteLinesByIdSoft(Long id) {
        // cet id est l'id de la ligne d'achat
        LigneAchat ligneAchat = ligneAchatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LigneAchat", id));
        String reference = "ACH_" + ligneAchat.getAchat().getId() + "_LIG_" + ligneAchat.getId() + "_DEL";

        LocalDateTime dateCreate = LocalDateTime.now();

        MouvementStockPostDto mvtInverse = MouvementStockPostDto.builder()
                .reference(reference)
                .produitId(ligneAchat.getProduit().getId())
                .quantite(ligneAchat.getQuantite())
                .commentaire("Eve inverse pour ajuster le stock ")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("RETOUR_FOURNISSEUR")
                .idEvenementOrigine(ligneAchat.getAchat().getId())
                .idLigneOrigine(ligneAchat.getId())
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneAchat.setActif(false);
        ligneAchatRepository.save(ligneAchat);
        updateTotalAchat(ligneAchat.getAchat().getId());

    }

    @Transactional
    public void updateTotalAchat(Long achatId) {
        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new EntityNotFoundException("Achat", achatId));

        Double total = ligneAchatRepository.sumTotalByAchatId(achatId);
        achat.setMontantTotal(total != null ? total : 0.0);

        achatRepository.save(achat);
    }
}
