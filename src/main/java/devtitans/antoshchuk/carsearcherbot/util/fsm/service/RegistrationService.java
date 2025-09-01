package devtitans.antoshchuk.carsearcherbot.util.fsm.service;

import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationEvents;
import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationService {
    private final StateMachineFactory<RegistrationStates, RegistrationEvents> factory;
    private final Map<Long, StateMachine<RegistrationStates, RegistrationEvents>> userMachines = new ConcurrentHashMap<>();

    public RegistrationService(StateMachineFactory<RegistrationStates, RegistrationEvents> factory) {
        this.factory = factory;
    }

    public StateMachine<RegistrationStates, RegistrationEvents> getUserMachine(long userId) {
        return userMachines.computeIfAbsent(userId, id -> {
            StateMachine<RegistrationStates, RegistrationEvents> machine = factory.getStateMachine(Long.toString(userId));
            machine.start();
            return machine;
        });
    }

    public void completeRegistration(long userId) {
        StateMachine<RegistrationStates, RegistrationEvents> machine = getUserMachine(userId);
        machine.stop();
        userMachines.remove(userId);
    }

    public void sendEvent(Long userId, RegistrationEvents event) {
        getUserMachine(userId).sendEvent(event);
    }
}
