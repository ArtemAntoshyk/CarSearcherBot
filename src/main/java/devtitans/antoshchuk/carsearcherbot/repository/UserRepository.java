package devtitans.antoshchuk.carsearcherbot.repository;

import devtitans.antoshchuk.carsearcherbot.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(@NotNull String phone);

    Optional<User> getUsersByPhone(String phone);

    Optional<User> getUsersByUserId(long userId);
}
