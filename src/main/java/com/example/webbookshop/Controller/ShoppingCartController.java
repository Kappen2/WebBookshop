package com.example.webbookshop.Controller;

import com.example.webbookshop.DTO.ShoppingCartDTO;
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
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    @GetMapping("/{id}")
    public ShoppingCart getShoppingCartById(@PathVariable Long id) {
        // Implement logic to fetch shopping cart from repository by ID
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getShoppingCartByUserId(@PathVariable Long userId) {
        // Step 1: Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }

        // Step 2: Check if a shopping cart is linked to the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shopping cart found for user with ID " + userId);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        // Step 1: Validate if the specified user exists
        Optional<User> userOptional = userRepository.findById(shoppingCartDTO.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + shoppingCartDTO.getUserId() + " not found.");
        }

        // Step 2: Check if the user already has a shopping cart
        ResponseEntity<?> responseEntity = getShoppingCartByUserId(shoppingCartDTO.getUserId());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with ID " + shoppingCartDTO.getUserId() + " already has a shopping cart.");
        }

        // Step 3: Create a new shopping cart linked to the user
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setPrice(shoppingCartDTO.getPrice());

        User user = userOptional.get();
        shoppingCart.setUser(user);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Return the created shopping cart
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShoppingCart);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateShoppingCartPrice(@PathVariable Long id, @RequestParam double price) {
        // Step 1: Check if the shopping cart exists
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(id);
        if (shoppingCartOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart with ID " + id + " not found.");
        }

        // Step 2: Update the price of the shopping cart
        ShoppingCart shoppingCart = shoppingCartOptional.get();
        shoppingCart.setPrice(price);
        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Return the updated shopping cart
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @PatchMapping("/update/user/{userId}")
    public ResponseEntity<?> updateShoppingCartPriceByUserId(@PathVariable Long userId, @RequestParam double price) {
        // Step 1: Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }

        // Step 2: Check if the shopping cart exists for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shopping cart found for user with ID " + userId);
        }

        // Step 3: Update the price of the shopping cart
        shoppingCart.setPrice(price);
        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Return the updated shopping cart
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShoppingCart(@PathVariable Long id) {
        // Step 1: Check if the shopping cart exists
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(id);
        if (shoppingCartOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart with ID " + id + " not found.");
        }

        // Step 2: Delete the shopping cart
        shoppingCartRepository.deleteById(id);

        // Return a success message
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<?> deleteShoppingCartByUserId(@PathVariable Long userId) {
        // Step 1: Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }

        // Step 2: Check if the shopping cart exists for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shopping cart found for user with ID " + userId);
        }

        // Step 3: Delete the shopping cart
        shoppingCartRepository.delete(shoppingCart);

        // Return a success message
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/checkout/{userId}")
    public ResponseEntity<?> checkoutShoppingCart(@PathVariable Long userId) {
        // Step 1: Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }

        // Step 2: Get the shopping cart tied to the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shopping cart found for user with ID " + userId);
        }

        // Step 3: Check if the user has enough balance to check out
        User user = userOptional.get();
        if (user.getBalance() >= shoppingCart.getPrice()) {
            // Subtract the price from the user's balance
            user.setBalance(user.getBalance() - shoppingCart.getPrice());
            userRepository.save(user);

            // Delete the shopping cart
            shoppingCartRepository.delete(shoppingCart);

            return ResponseEntity.ok("Checkout successful. Your new balance is: " + user.getBalance());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance. You don't have enough money on your account.");
        }
    }
}
