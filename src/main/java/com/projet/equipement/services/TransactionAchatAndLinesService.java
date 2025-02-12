package com.projet.equipement.services;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.mapper.LigneAchatMapper;
import com.projet.equipement.repository.AchatRepository;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.LigneAchatRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionAchatAndLinesService {

    private final LigneAchatMapper ligneAchatMapper;
    private final LigneAchatRepository ligneAchatRepository;
    private final MouvementStockService mouvementStockService;
    private final AchatMapper achatMapper;
    private final AchatRepository achatRepository;
    private final EntityManager entityManager;

    public TransactionAchatAndLinesService(
                                           LigneAchatMapper ligneAchatMapper,
                                           LigneAchatRepository ligneAchatRepository,
                                           MouvementStockService mouvementStockService,
                                           AchatMapper achatMapper,
                                           AchatRepository achatRepository,
                                           EntityManager entityManager
    ) {
        this.ligneAchatMapper = ligneAchatMapper;
        this.ligneAchatRepository = ligneAchatRepository;
        this.mouvementStockService = mouvementStockService;
        this.achatMapper = achatMapper;
        this.achatRepository = achatRepository;
        this.entityManager = entityManager;
    }


    @Transactional
    public void updateTotalAchat(Long achatId) {
        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new EntityNotFoundException("Achat", achatId));

        Double total = ligneAchatRepository.sumTotalByAchatId(achatId);
        achat.setMontantTotal(total != null ? total : 0.0);

        achatRepository.save(achat);
    }


    @Transactional
    public void softDeleteAchat(Long id) {

        List<LigneAchat> ligneAchats = this.findByAchatId(id);
        for (LigneAchat ligneAchat : ligneAchats) {
            this.deleteLinesByIdSoft(ligneAchat.getId());
        }

        this.deleteAchatByIdSoft(id);
    }

    public Page<LigneAchatGetDto> findAllLine(Pageable pageable) {
        Page<LigneAchat> allLine = ligneAchatRepository.findAllLine(pageable);
        return allLine.map(ligneAchatMapper::toDto);
    }

    public LigneAchatGetDto findLigneAchatById(Long id) {
        return ligneAchatMapper.toDto(ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id)));
    }


    /**
     * Soft delete
     *
     * @param id id de la ligne
     */
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
                .idEvenementOrigine(Math.toIntExact(ligneAchat.getAchat().getId()))
                .idLigneOrigine(Math.toIntExact(ligneAchat.getId()))
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneAchat.setActif(false);
        ligneAchatRepository.save(ligneAchat);
        updateTotalAchat(ligneAchat.getAchat().getId());

    }

    /**
     * Obtient les lignes de l'achat par l'id de l'achat
     *
     * @param id id achat
     * @return Page de lignes
     */
    public Page<LigneAchatGetDto> findByAchatId(Long id, Pageable pageable) {
        Page<LigneAchat> byAchatId = ligneAchatRepository.findByAchatId(id, pageable);
        return byAchatId.map(ligneAchatMapper::toDto);
    }

    /**
     * Obtient les lignes de l'achat par l'id de l'achat
     *
     * @param id id achat
     * @return List de ligne
     */
    public List<LigneAchat> findByAchatId(Long id) {
        return ligneAchatRepository.findByAchatId(id);
    }


    public Page<AchatGetDto> findAllAchat(Pageable pageable) {
        Page<Achat> allPage = achatRepository.findAllPage(pageable);
        return allPage.map(achatMapper::toDto);
    }

    public AchatGetDto findAchatById(Long id) {
        return achatMapper.toDto(achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id)));
    }


    // Suppression de l'achat
    public void deleteAchatByIdSoft(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        achat.setActif(false);  // Soft delete
        achatRepository.save(achat);
    }

    @Transactional
    public LigneAchatGetDto saveLigneAchat(LigneAchatPostDto ligneAchatPostDto) {
        Long achatId = ligneAchatPostDto.getAchatId();


        LigneAchat ligneAchatToPost = ligneAchatMapper.toEntity(ligneAchatPostDto);

        LigneAchat saveLigneAchat = ligneAchatRepository.save(ligneAchatToPost);

        // Modifie le total de l'achat lors de la save d'une ligne
        entityManager.refresh(saveLigneAchat);

        updateTotalAchat(saveLigneAchat.getAchat().getId());

        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.save(MouvementStockPostDto.builder()
                .reference("ACH_" + achatId + "_LIG_" + saveLigneAchat.getId())
                .produitId(ligneAchatPostDto.getProduitId())
                .quantite(ligneAchatPostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un achat")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("ACHAT_MARCHANDISE")
                .idEvenementOrigine(Math.toIntExact(saveLigneAchat.getAchat().getId()))
                .idLigneOrigine(Math.toIntExact(saveLigneAchat.getId()))
                .build());


        return ligneAchatMapper.toDto(saveLigneAchat);
    }

    @Transactional
    public AchatGetDto saveAchat(AchatPostDto achatPostDto) {

        Achat achat = achatMapper.toEntity(achatPostDto);
        // Sauvegarde de l'achat pour obtenir l'id
        Achat achatSansTotal = achatRepository.save(achat);
        entityManager.refresh(achatSansTotal);
        // Mise a jour du total
        updateTotalAchat(achatSansTotal.getId());

        return achatMapper.toDto(achatSansTotal);
    }

    @Transactional
    public LigneAchatGetDto updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id) {
        LigneAchat ligneAchat = ligneAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("LigneAchat", id));

        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat);
        LigneAchat savedLine = ligneAchatRepository.save(ligneAchat);
        //mise a jour du total
        updateTotalAchat(savedLine.getAchat().getId());
        return ligneAchatMapper.toDto(savedLine);
    }



    public AchatGetDto updateAchat(AchatUpdateDto achatUpdateDto, Long id) {
        Achat achat = achatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Achat", id));
       //Mapping
        achatMapper.updateDto(achatUpdateDto, achat);
        return achatMapper.toDto(achatRepository.save(achat));
    }
}
