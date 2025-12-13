package com.projet.equipement.services;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.enumeration.AchatEnum;
import com.projet.equipement.enumeration.AchatEvent;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.repository.AchatRepository;
import com.projet.equipement.repository.EmployeRepository;
import com.projet.equipement.repository.LigneAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AchatService {

    private final AchatRepository achatRepository;
    private final AchatMapper achatMapper;
    private final EmployeRepository employeRepository;
    private final LigneAchatRepository ligneAchatRepository;
    private final LigneAchatService ligneAchatService;
    private final MouvementStockService mouvementStockService;
    private final StateMachineFactory<AchatEnum, AchatEvent> stateMachineFactory;
    private final EtatAchatService etatAchatService;

    public AchatService(AchatRepository achatRepository,
                        AchatMapper achatMapper,
                        EmployeRepository employeRepository,
                        LigneAchatRepository ligneAchatRepository,
                        LigneAchatService ligneAchatService,
                        MouvementStockService mouvementStockService,
                        StateMachineFactory<AchatEnum, AchatEvent> stateMachineFactory, EtatAchatService etatAchatService) {
        this.achatRepository = achatRepository;
        this.achatMapper = achatMapper;
        this.employeRepository = employeRepository;
        this.ligneAchatRepository = ligneAchatRepository;
        this.ligneAchatService = ligneAchatService;
        this.mouvementStockService = mouvementStockService;
        this.stateMachineFactory = stateMachineFactory;
        this.etatAchatService = etatAchatService;
    }

    public Page<AchatGetDto> findAll(Pageable pageable) {
        Page<Achat> allPage = achatRepository.findAllPage(pageable);
        return allPage.map(achatMapper::toDto);
    }

    public  AchatGetDto findById(Long id) {
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));
        return achatMapper.toDto(achat);
    }

    public  Achat findAchatById(Long id) {
        return achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));
    }

    public AchatGetDto getAchatById(Long id){
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));
        return achatMapper.toDto(achat);
    }

    public AchatGetDto save(AchatPostDto achatPostDto){
        Achat achat = achatMapper.toEntity(achatPostDto);
        achat.setEmploye(employeRepository.findById(achatPostDto.getEmployeId()).orElseThrow(()-> new EntityNotFoundException("Employe", achatPostDto.getEmployeId())));
        achat.setTenantId(TenantContext.getTenantId());
        achat.setMontantTotal(0.0);
        return achatMapper.toDto(achatRepository.save(achat));
    }

    public Achat save(Achat achat){
        return achatRepository.save(achat) ;
    }

    public AchatGetDto update(AchatUpdateDto achatUpdateDto, Long id){
        Achat achat = achatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Achat", id));

        achatMapper.updateDto(achatUpdateDto, achat);

        return achatMapper.toDto(achatRepository.save(achat));
    }

    @Transactional
    public void softDeleteAchat(Long id) {

        List<LigneAchat> ligneAchats = ligneAchatRepository.findByAchatId(id);
        for (LigneAchat ligneAchat : ligneAchats) {
            ligneAchatService.deleteLinesByIdSoft(ligneAchat.getId());
        }

        this.deleteAchatByIdSoft(id);
    }

    public void deleteAchatByIdSoft(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        achat.setActif(false);  // Soft delete
        achatRepository.save(achat);
    }

    public void validerAchat(Long id) {

        Achat achat = findAchatById(id);

        StateMachine<AchatEnum, AchatEvent> sm = stateMachineFactory.getStateMachine(achat.getId().toString());
        sm.getStateMachineAccessor().doWithAllRegions(access ->
                access.resetStateMachine(new DefaultStateMachineContext<>(
                        AchatEnum.valueOf(achat.getEtat().getLibelle()),
                        null, null, null)));

        boolean ok = sm.sendEvent(AchatEvent.VALIDER);
        if (!ok) throw new IllegalStateException("Transition non autorisée");

        String etatMachine = sm.getState().getId().toString();

        achat.setEtat(etatAchatService.findByLibelle(etatMachine));

        save(achat);

        lancerMvmStkAchat(id);

    }


    private void lancerMvmStkAchat(Long id) {
        Achat achat = findAchatById(id);

        achat.getLigneAchats().forEach(ligneAchat -> {

            LocalDateTime dateCreate = LocalDateTime.now();
            // Enregistrement du mouvement de stock via le service dédié
            mouvementStockService.save(MouvementStockPostDto.builder()
                    .reference("ACH_" + achat.getId() + "_LIG_" + ligneAchat.getId())
                    .produitId(ligneAchat.getProduit().getId())
                    .quantite(ligneAchat.getQuantite())
                    .commentaire("Généré à partir de la ligne d'un achat")
                    .createdAt(dateCreate)
                    .dateMouvement(dateCreate)
                    .typeMouvementCode("ACHAT_MARCHANDISE")
                    .idEvenementOrigine(ligneAchat.getAchat().getId())
                    .idLigneOrigine(ligneAchat.getId())
                    .build());
        });
    }


}
