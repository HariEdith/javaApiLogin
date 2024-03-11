package net.javaguides.springboot.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}