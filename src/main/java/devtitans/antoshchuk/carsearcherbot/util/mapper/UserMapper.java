package devtitans.antoshchuk.carsearcherbot.util.mapper;

import devtitans.antoshchuk.carsearcherbot.entity.User;
import devtitans.antoshchuk.carsearcherbot.util.fsm.context.RegistrationContext;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapRegContextToUser(RegistrationContext context) {
        User user = new User();
        user.setFirstName(context.getFirstName());
        user.setLastName(context.getLastName());
        user.setPhone(context.getPhone());
        user.setEmail(context.getEmail());
        user.setCity("");
        return user;
    }
}
