package net.javaguides.springboot.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.entity.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.javaguides.springboot.repo.UserRepository;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveUser() {
        UserDTO user = new UserDTO();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        UserDTO savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDTO> retrievedUser = userService.getUserById(userId);

        assertTrue(retrievedUser.isPresent());
        assertEquals(userId, retrievedUser.get().getId());
        assertEquals("testuser", retrievedUser.get().getUsername());
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO());
        userList.add(new UserDTO());

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> retrievedUsers = userService.getAllUsers();

        assertEquals(2, retrievedUsers.size());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testAuthenticateUser_Success() {
        String username = "testuser";
        String password = "testpassword";
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        Optional<UserDTO> authenticatedUser = userService.authenticateUser(username, password);

        assertTrue(authenticatedUser.isPresent());
        assertEquals(username, authenticatedUser.get().getUsername());
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        String username = "nonexistentuser";
        String password = "testpassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser(username, password);
        });
    }

    @Test
     void testAuthenticateUser_IncorrectPassword() {
        String username = "testuser";
        String password = "incorrectpassword";
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword("correctpassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        Optional<UserDTO> authenticatedUser = userService.authenticateUser(username, password);

        assertFalse(authenticatedUser.isPresent());
    }
}
