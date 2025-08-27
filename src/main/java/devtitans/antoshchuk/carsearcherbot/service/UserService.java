package devtitans.antoshchuk.carsearcherbot.service;

import devtitans.antoshchuk.carsearcherbot.entity.User;
import devtitans.antoshchuk.carsearcherbot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user){
        if(!userRepository.existsByPhone(user.getPhone())){
            userRepository.save(user);
        }
        throw new RuntimeException("User already exists");
    }

    public User getUserByPhone(String phone){
        return userRepository.getUsersByPhone(phone).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User getUSerByUserId(long userId){
        return userRepository.getUsersByUserId(userId).orElseThrow(()->new RuntimeException("User not found"));
    }
}
