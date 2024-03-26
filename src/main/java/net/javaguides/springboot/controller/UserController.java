package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.UserDTO;
import net.javaguides.springboot.request.UserRequestModel;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestModel userRequest) {
        UserDTO userDTO = convertToUserDTO(userRequest);
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestModel userRequest) {
        UserDTO userDTO = convertToUserDTO(userRequest);
        Optional<UserDTO> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserDTO updatedUser = user.get();
            updatedUser.setUsername(userDTO.getUsername());
            updatedUser.setEmail(userDTO.getEmail());
            updatedUser.setPassword(userDTO.getPassword());
            userService.saveUser(updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserRequestModel userRequest) {
        UserDTO userDTO = convertToUserDTO(userRequest);
        Optional<UserDTO> authenticatedUser = userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());
        if (authenticatedUser.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    private UserDTO convertToUserDTO(UserRequestModel userRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRequest.getUsername());
        userDTO.setEmail(userRequest.getEmail());
        userDTO.setPassword(userRequest.getPassword());
        return userDTO;
    }
}
