package com.projet.equipement.services;

// VenteService.java

import com.projet.equipement.entity.Vente;
import com.projet.equipement.enumeration.SalesEvent;
import com.projet.equipement.enumeration.SalesState;
import com.projet.equipement.repository.VenteRepository;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class VenteService {

    private final VenteRepository venteRepository;


    public VenteService(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    /**
     * Valide la vente.
     */
    @Transactional
    public void validerVente( ) {
//        Vente vente = venteRepository.findById(venteId)
//                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));
//        // Réinitialiser la machine d'état pour la vente en cours
//        stateMachine.getStateMachineAccessor().doWithAllRegions(access ->
//                access.setInitialEnabled(
//                        new DefaultStateMachineContext<>(vente.getEtat(), null, null, null)
//                )
//        );
//        // Lancer l'événement VALIDATE

    }

    /**
     * Met à jour l'état de la vente suite à un paiement ou un retour.
     * Vous pouvez appeler cette méthode après un paiement ou retour pour synchroniser l'état.
     */


    // D'autres méthodes pour gérer la vente globale
}
