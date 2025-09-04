package devtitans.antoshchuk.carsearcherbot.util.fsm.context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationContext {
    private long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
