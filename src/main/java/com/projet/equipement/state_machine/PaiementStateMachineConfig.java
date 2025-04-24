package com.projet.equipement.state_machine;

import com.projet.equipement.enumeration.PaiementEnum;
import com.projet.equipement.enumeration.PaiementTransitionEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine(name = "PaiementStateMachineConfig")
public class PaiementStateMachineConfig extends StateMachineConfigurerAdapter<PaiementEnum, String> {


    @Override
    public void configure(StateMachineStateConfigurer<PaiementEnum, String> states) throws Exception {
        states
                .withStates()
                .initial(PaiementEnum.EN_ATTENTE)  // Etat initial
                .state(PaiementEnum.EFFECTUE)
                .state(PaiementEnum.REFUSE)
                .state(PaiementEnum.PARTIEL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaiementEnum, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source( (PaiementEnum.EN_ATTENTE)).target( (PaiementEnum.EFFECTUE))
                .event(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_EFFECTUE))

                .and()
                .withExternal()
                .source( (PaiementEnum.EN_ATTENTE))
                .target( (PaiementEnum.REFUSE))
                .event(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_REFUSE))

                .and()
                .withExternal()
                .source(PaiementEnum.EN_ATTENTE)
                .target(PaiementEnum.PARTIEL)
                .event(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_PARTIEL))

                .and()
                .withExternal()
                .source(PaiementEnum.PARTIEL)
                .target(PaiementEnum.EFFECTUE)
                .event(String.valueOf(PaiementTransitionEnum.PARTIEL_EFFECTUE));
    }
}
