package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.UserDTO;
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
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO(1L, "user1", "user1@example.com", "password1"));

        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<UserDTO>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    void testGetUserById() {
        UserDTO user = new UserDTO(1L, "user1", "user1@example.com", "password1");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> responseEntity = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testCreateUser() {
        UserDTO user = new UserDTO(1L, "user1", "user1@example.com", "password1");
        when(userService.saveUser(user)).thenReturn(user);

        ResponseEntity<UserDTO> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }



    @Test
    void testUpdateUser() {
        UserDTO user = new UserDTO(1L, "user1", "user1@example.com", "password1");
        UserDTO updatedUser = new UserDTO(1L, "updatedUser1", "updatedUser1@example.com", "updatedPassword1");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userService.saveUser(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<UserDTO> responseEntity = userController.updateUser(1L, updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testUpdateUserNotFound() {
        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.updateUser(2L, new UserDTO());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userService, times(1)).getUserById(2L);
        verify(userService, never()).saveUser(any(UserDTO.class));
    }

    @Test
    void testLoginUser() {
        UserDTO user = new UserDTO(1L, "user1", "user1@example.com", "password1");
        when(userService.authenticateUser("user1", "password1")).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> responseEntity = userController.loginUser(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
