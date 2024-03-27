package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.UserDTO;
import net.javaguides.springboot.request.UserRequestModel;
import net.javaguides.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {

        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(1L, "testuser1", "test1@example.com", "password1"));
        users.add(new UserDTO(2L, "testuser2", "test2@example.com", "password2"));

        when(userService.getAllUsers()).thenReturn(users);


        ResponseEntity<List<UserDTO>> responseEntity = userController.getAllUsers();


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserDTO user = new UserDTO(userId, "testuser", "test@example.com", "password");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testUpdateUser() {

        Long userId = 1L;
        UserRequestModel userRequest = new UserRequestModel();
        userRequest.setUsername("updateduser");
        userRequest.setEmail("updated@example.com");
        userRequest.setPassword("updatedpassword");

        UserDTO updatedUserDTO = new UserDTO(userId, userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword());
        when(userService.getUserById(userId)).thenReturn(Optional.of(updatedUserDTO));
        when(userService.saveUser(updatedUserDTO)).thenReturn(updatedUserDTO);

        ResponseEntity<UserDTO> responseEntity = userController.updateUser(userId, userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUserDTO, responseEntity.getBody());
        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).saveUser(updatedUserDTO);
    }
    @Test
    void testLoginUser() {

        UserRequestModel userRequest = new UserRequestModel();
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRequest.getUsername());
        userDTO.setEmail(userRequest.getEmail());
        userDTO.setPassword(userRequest.getPassword());

        when(userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword())).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> responseEntity = userController.loginUser(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).authenticateUser(userDTO.getUsername(), userDTO.getPassword());
    }

    @Test
    void testDeleteUser() {

        Long userId = 1L;

        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
