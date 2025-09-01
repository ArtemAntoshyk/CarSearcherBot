package devtitans.antoshchuk.carsearcherbot.util.fsm.config;

import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationEvents;
import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationStates;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class RegistrationStateMachineConfig extends StateMachineConfigurerAdapter<RegistrationStates, RegistrationEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<RegistrationStates, RegistrationEvents> states) throws Exception {
        states
                .withStates()
                .initial(RegistrationStates.START)
                .state(RegistrationStates.ENTER_NAME)
                .state(RegistrationStates.ENTER_EMAIL)
                .state(RegistrationStates.ENTER_PHONE_NUMBER)
                .state(RegistrationStates.CONFIRM)
                .state(RegistrationStates.COMPLETED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RegistrationStates, RegistrationEvents> transitions) throws Exception {
        transitions
                .withExternal().source(RegistrationStates.START).target(RegistrationStates.ENTER_NAME)
                .event(RegistrationEvents.START_REGISTRATION)
                .and()
                .withExternal().source(RegistrationStates.ENTER_NAME).target(RegistrationStates.ENTER_EMAIL)
                .event(RegistrationEvents.NAME_ENTERED)
                .and()
                .withExternal().source(RegistrationStates.ENTER_EMAIL).target(RegistrationStates.ENTER_PHONE_NUMBER)
                .event(RegistrationEvents.EMAIL_ENTERED)
                .and()
                .withExternal().source(RegistrationStates.ENTER_PHONE_NUMBER).target(RegistrationStates.CONFIRM)
                .event(RegistrationEvents.PHONE_NUMBER_ENTERED)
                .and()
                .withExternal().source(RegistrationStates.CONFIRM).target(RegistrationStates.COMPLETED)
                .event(RegistrationEvents.CONFIRMED);
    }
}
