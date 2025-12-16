package com.projet.equipement.services;

import com.projet.equipement.entity.Avoir;
import com.projet.equipement.entity.EtatAvoir;
import com.projet.equipement.enumeration.avoir.AvoirEnum;
import com.projet.equipement.enumeration.avoir.AvoirEvent;
import com.projet.equipement.repository.EtatAvoirRepository;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class AvoirEnumMachineService {

    private final StateMachineFactory<AvoirEnum, AvoirEvent> factory;
    private final EtatAvoirRepository etatAvoirRepository;

    public AvoirEnumMachineService(
            StateMachineFactory<AvoirEnum, AvoirEvent> factory,
            EtatAvoirRepository etatAvoirRepository
    ) {
        this.factory = factory;
        this.etatAvoirRepository = etatAvoirRepository;
    }

    public void fireEvent(Avoir avoir, AvoirEvent event) {

        StateMachine<AvoirEnum, AvoirEvent> sm = factory.getStateMachine();

        // 1. positionner la machine sur l’état courant DB
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(access ->
                        access.resetStateMachine(
                                new DefaultStateMachineContext<>(
                                        AvoirEnum.valueOf(avoir.getEtat().getLibelle()),
                                        null, null, null
                                )
                        )
                );
        sm.start();

        // 2. déclencher l’événement
        boolean accepted = sm.sendEvent(event);

        if (!accepted) {
            throw new IllegalStateException(
                    "Transition non autorisée : " + event
            );
        }

        // 3. persister le nouvel état
        AvoirEnum newState = sm.getState().getId();

        EtatAvoir etatDb = etatAvoirRepository
                .findByLibelle(newState.name())
                .orElseThrow(() -> new IllegalStateException(
                        "ETAT_AVOIR manquant : " + newState
                ));

        avoir.setEtat(etatDb);
    }
}
