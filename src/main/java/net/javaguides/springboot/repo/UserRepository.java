package net.javaguides.springboot.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.entity.UserDTO;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> findByUsername(String username);//method
}