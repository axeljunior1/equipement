package com.projet.equipement.state_machine;

import com.projet.equipement.enumeration.avoir.AvoirEnum;
import com.projet.equipement.enumeration.avoir.AvoirEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "AvoirEnumMachineConfig")
public class ConfigurationStateMachineAvoir extends EnumStateMachineConfigurerAdapter<AvoirEnum, AvoirEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<AvoirEnum, AvoirEvent> states) throws Exception {
        states
                .withStates()
                .initial(AvoirEnum.ACTIF)
                .state(AvoirEnum.PARTIELLEMENT_UTILISE)
                .state(AvoirEnum.UTILISE)
                .state(AvoirEnum.ANNULE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AvoirEnum, AvoirEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(AvoirEnum.ACTIF)
                .target(AvoirEnum.PARTIELLEMENT_UTILISE)
                .event(AvoirEvent.UTILISER_PARTIELLEMENT)
                .and()
                .withExternal()
                .source(AvoirEnum.ACTIF)
                .target(AvoirEnum.UTILISE)
                .event(AvoirEvent.UTILISER_TOTALEMENT)
                .and()
                .withExternal()
                .source(AvoirEnum.ACTIF)
                .target(AvoirEnum.ANNULE)
                .event(AvoirEvent.ANNULER)

                // PARTIELLEMENT_UTILISE
                .and()
                .withExternal()
                .source(AvoirEnum.PARTIELLEMENT_UTILISE)
                .target(AvoirEnum.UTILISE)
                .event(AvoirEvent.UTILISER_TOTALEMENT)
                .and()
                .withExternal()
                .source(AvoirEnum.PARTIELLEMENT_UTILISE)
                .target(AvoirEnum.ANNULE)
                .event(AvoirEvent.ANNULER_LE_PARTIEL);

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<AvoirEnum, AvoirEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

}
