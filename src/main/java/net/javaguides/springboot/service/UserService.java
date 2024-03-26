package net.javaguides.springboot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.javaguides.springboot.entity.UserDTO;
import net.javaguides.springboot.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;

    public UserDTO saveUser(UserDTO user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public Optional<UserDTO> authenticateUser(String username, String password) {
        Optional<UserDTO> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOptional;
            }
        }
        throw new UserAuthenticationException("Invalid username or password");
    }

    public static class UserAuthenticationException extends RuntimeException {
        public UserAuthenticationException(String message) {
            super(message);
        }
    }

}
