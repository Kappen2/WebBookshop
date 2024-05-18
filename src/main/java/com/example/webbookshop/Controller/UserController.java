package com.example.webbookshop.Controller;

import com.example.webbookshop.DTO.LoginRequest;
import com.example.webbookshop.DTO.UserDTO;
import com.example.webbookshop.Model.ShoppingCart;
import com.example.webbookshop.Model.User;
import com.example.webbookshop.Repository.ShoppingCartRepository;
import com.example.webbookshop.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public UserController(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        // Convert UserDTO to User entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBalance(userDTO.getBalance());
        user.setAdminAccess(userDTO.getAdminAccess());

        // Save the user
        User savedUser = userRepository.save(user);

        // Create a shopping cart tied to the user
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updatedUserDTO.getUsername() != null) {
                user.setUsername(updatedUserDTO.getUsername());
            }
            if (updatedUserDTO.getPassword() != null) {
                user.setPassword(updatedUserDTO.getPassword());
            }
            if (updatedUserDTO.getFirstName() != null) {
                user.setFirstName(updatedUserDTO.getFirstName());
            }
            if (updatedUserDTO.getLastName() != null) {
                user.setLastName(updatedUserDTO.getLastName());
            }
            if (updatedUserDTO.getBalance() != null) {
                user.setBalance(updatedUserDTO.getBalance());
            }
            if (updatedUserDTO.getAdminAccess() != null) {
                user.setAdminAccess(updatedUserDTO.getAdminAccess());
            }

            // Update other fields as needed
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
