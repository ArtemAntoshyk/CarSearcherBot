package devtitans.antoshchuk.carsearcherbot.repository;

import devtitans.antoshchuk.carsearcherbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

}
