package com.projet.equipement.stateMachine;

import com.projet.equipement.enumeration.VenteEnum;
import com.projet.equipement.enumeration.VenteTransitionEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine(name = "VenteStateMachineConfig")
public class VenteStateMachineConfig extends StateMachineConfigurerAdapter<VenteEnum, String> {


    @Override
    public void configure(StateMachineStateConfigurer<VenteEnum, String> states) throws Exception {
        states
                .withStates()
                .initial(VenteEnum.CONFIRMEE)  // Etat initial
                .state(VenteEnum.CONFIRMEE)
                .state(VenteEnum.PAYEE)
                .state(VenteEnum.ANNULEE)
                .state(VenteEnum.REMBOURSEE)
                .state(VenteEnum.AVOIR_GENERE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<VenteEnum, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source( (VenteEnum.CONFIRMEE))
                .target( (VenteEnum.PAYEE))
                .event(String.valueOf(VenteTransitionEnum.CONFIRMEE_PAYEE))
                .and()
                .withExternal()
                .source( (VenteEnum.CONFIRMEE))
                .target( (VenteEnum.ANNULEE))

                .event(String.valueOf(VenteTransitionEnum.CONFIRMEE_ANNULEE))
                .and()
                .withExternal()
                .source(VenteEnum.PAYEE)
                .target(VenteEnum.REMBOURSEE)
                .event(String.valueOf(VenteTransitionEnum.PAYEE_REMBOURSEE))
                .and()
                .withExternal()
                .source(VenteEnum.PAYEE)
                .target(VenteEnum.AVOIR_GENERE)
                .event(String.valueOf(VenteTransitionEnum.PAYEE_AVOIR_GENERE));
    }
}
