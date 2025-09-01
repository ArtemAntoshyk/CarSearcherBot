package devtitans.antoshchuk.carsearcherbot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Column(unique = true)
    private String username;
    @Column(name = "user_id", nullable = false, unique = true)
    private long userId;
    @NotNull
    @Column(unique = true)
    private String phone;
    @NotNull
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime lastActive;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        lastActive = LocalDateTime.now();
    }
}
