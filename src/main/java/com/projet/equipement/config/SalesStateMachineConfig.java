package com.projet.equipement.config;

// SalesStateMachineConfig.java

import com.projet.equipement.enumeration.SalesEvent;
import com.projet.equipement.enumeration.SalesState;
import com.projet.equipement.exceptions.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import javax.swing.plaf.nimbus.State;
import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class SalesStateMachineConfig extends EnumStateMachineConfigurerAdapter<SalesState, SalesEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SalesStateMachineConfig.class);


    @Override
    public void configure(StateMachineStateConfigurer<SalesState, SalesEvent> states) throws Exception {
        states
                .withStates()
                .initial(SalesState.CREATED)
                .states(EnumSet.allOf(SalesState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SalesState, SalesEvent> transitions) throws Exception {
        transitions
                // Validation de la vente
                .withExternal()
                .source(SalesState.CREATED)
                .target(SalesState.VALIDATED)
                .event(SalesEvent.VALIDATE)
                .and()
                // Paiement partiel (depuis un état validé)
                .withExternal()
                .source(SalesState.VALIDATED)
                .target(SalesState.PARTIALLY_PAID)
                .event(SalesEvent.PAY_PARTIAL)
                .and()
                // Paiement complet depuis état VALIDATED (paiement en une fois)
                .withExternal()
                .source(SalesState.VALIDATED)
                .target(SalesState.FULLY_PAID)
                .event(SalesEvent.PAY_FULL)
                .and()
                // Paiement complet depuis PARTIALLY_PAID
                .withExternal()
                .source(SalesState.PARTIALLY_PAID)
                .target(SalesState.FULLY_PAID)
                .event(SalesEvent.PAY_FULL)
                .and()
                // Retour partiel sur la vente
                .withExternal()
                .source(SalesState.FULLY_PAID) // ou PARTIALLY_PAID selon la logique métier
                .target(SalesState.PARTIALLY_REFUNDED)
                .event(SalesEvent.REFUND_PARTIAL)
                // Ici, vous pouvez ajouter un guard pour vérifier que la quantité retournée < quantité vendue
                .and()
                // Retour total depuis FULLY_PAID ou depuis PARTIALLY_REFUNDED
                .withExternal()
                .source(SalesState.FULLY_PAID)
                .target(SalesState.FULLY_REFUNDED)
                .event(SalesEvent.REFUND_FULL)
                .and()
                .withExternal()
                .source(SalesState.PARTIALLY_REFUNDED)
                .target(SalesState.FULLY_REFUNDED)
                .event(SalesEvent.REFUND_FULL);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<SalesState, SalesEvent> config) throws Exception {
        config
                .withConfiguration()
                .listener(listener())
                .autoStartup(true)
                ;
    }

    private StateMachineListener<SalesState, SalesEvent> listener() {
        return new StateMachineListenerAdapter<SalesState, SalesEvent>() {
            @Override
            public void transition(Transition<SalesState, SalesEvent> transition) {
                logger.warn("Move from: {} to: {}",
                        transition.getSource().getId(),
                        transition.getTarget().getId());
            }
        };
    }


}
