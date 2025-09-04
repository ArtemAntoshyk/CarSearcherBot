package devtitans.antoshchuk.carsearcherbot.service;

import devtitans.antoshchuk.carsearcherbot.entity.User;
import devtitans.antoshchuk.carsearcherbot.repository.UserRepository;
import devtitans.antoshchuk.carsearcherbot.util.fsm.context.RegistrationContext;
import devtitans.antoshchuk.carsearcherbot.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper = new UserMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user){
        if(!userRepository.existsByPhone(user.getPhone())){
            userRepository.save(user);
            return;
        }
        throw new RuntimeException("User already exists");
    }

    public User createUser(RegistrationContext context, String username, Long userId){
        User user = userMapper.mapRegContextToUser(context);
        user.setUserId(userId);
        user.setUsername(username);
        return user;
    }

    public User getUserByPhone(String phone){
        return userRepository.getUsersByPhone(phone).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User getUSerByUserId(long userId){
        return userRepository.getUsersByUserId(userId).orElseThrow(()->new RuntimeException("User not found"));
    }
}
