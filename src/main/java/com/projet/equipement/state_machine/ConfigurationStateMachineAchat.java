package com.projet.equipement.state_machine;

import com.projet.equipement.enumeration.AchatEnum;
import com.projet.equipement.enumeration.AchatEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "AchatStateMachineConfig")
public class ConfigurationStateMachineAchat extends EnumStateMachineConfigurerAdapter<AchatEnum, AchatEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<AchatEnum, AchatEvent> states) throws Exception {
        states
                .withStates()
                .initial(AchatEnum.CREEE)
                .state(AchatEnum.VALIDEE)
                .state(AchatEnum.RETOURNEE)
                .state(AchatEnum.ANNULEE)
                .end(AchatEnum.RETOURNEE)
                .end(AchatEnum.ANNULEE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AchatEnum, AchatEvent> transitions) throws Exception {
        transitions
                .withExternal().source(AchatEnum.CREEE).target(AchatEnum.VALIDEE).event(AchatEvent.VALIDER)
                .and().withExternal().source(AchatEnum.CREEE).target(AchatEnum.ANNULEE).event(AchatEvent.ANNULER)
                .and().withExternal().source(AchatEnum.VALIDEE).target(AchatEnum.RETOURNEE).event(AchatEvent.RETOURNER);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<AchatEnum, AchatEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

}
