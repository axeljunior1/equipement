package com.projet.equipement.state_machine;

import com.projet.equipement.enumeration.VenteEnum;
import com.projet.equipement.enumeration.VenteEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "venteStateMachineConfig")
public class ConfigurationStateMachineVente extends EnumStateMachineConfigurerAdapter<VenteEnum, VenteEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<VenteEnum, VenteEvent> states) throws Exception {
        states
                .withStates()
                .initial(VenteEnum.EN_ATTENTE_PAIEMENT)
                .state(VenteEnum.PAIEMENT_PARTIEL)
                .state(VenteEnum.VENTE_A_CREDIT)
                .state(VenteEnum.PAYEE)
                .state(VenteEnum.FERMEE)
                .state(VenteEnum.ANNULEE)
                .end(VenteEnum.FERMEE)
                .end(VenteEnum.ANNULEE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<VenteEnum, VenteEvent> transitions) throws Exception {
        transitions
                .withExternal().source(VenteEnum.EN_ATTENTE_PAIEMENT).target(VenteEnum.PAIEMENT_PARTIEL).event(VenteEvent.FAIRE_PAIEMENT_PARTIEL)
                .and().withExternal().source(VenteEnum.EN_ATTENTE_PAIEMENT).target(VenteEnum.PAYEE).event(VenteEvent.FAIRE_PAIEMENT)
                .and().withExternal().source(VenteEnum.EN_ATTENTE_PAIEMENT).target(VenteEnum.VENTE_A_CREDIT).event(VenteEvent.MARQUER_COMME_CREDIT)
                .and().withExternal().source(VenteEnum.PAIEMENT_PARTIEL).target(VenteEnum.PAYEE).event(VenteEvent.FAIRE_PAIEMENT_TOTAL)
                .and().withExternal().source(VenteEnum.VENTE_A_CREDIT).target(VenteEnum.PAIEMENT_PARTIEL).event(VenteEvent.FAIRE_PAIEMENT_DETTE)
                .and().withExternal().source(VenteEnum.VENTE_A_CREDIT).target(VenteEnum.PAYEE).event(VenteEvent.FAIRE_PAIEMENT_TOTAL_DETTE)
                .and().withExternal().source(VenteEnum.PAYEE).target(VenteEnum.FERMEE).event(VenteEvent.FERMER_VENTE)
                .and().withExternal().source(VenteEnum.EN_ATTENTE_PAIEMENT).target(VenteEnum.ANNULEE).event(VenteEvent.ANNULER_VENTE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<VenteEnum, VenteEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

}
