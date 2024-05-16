package com.example.webbookshop.Controller;

import com.example.webbookshop.DTO.BookOrderDTO;
import com.example.webbookshop.Model.Book;
import com.example.webbookshop.Model.BookOrder;
import com.example.webbookshop.Model.ShoppingCart;
import com.example.webbookshop.Repository.BookOrderRepository;
import com.example.webbookshop.Repository.BookRepository;
import com.example.webbookshop.Repository.ShoppingCartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/book-orders")
public class BookOrderController {

    private static final Logger logger = LoggerFactory.getLogger(BookOrderController.class);

    private final BookOrderRepository bookOrderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    public BookOrderController(BookOrderRepository bookOrderRepository, ShoppingCartRepository shoppingCartRepository, BookRepository bookRepository) {
        this.bookOrderRepository = bookOrderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<BookOrder> getAllBookOrders() {
        return bookOrderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOrder> getBookOrderById(@PathVariable Long id) {
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(id);
        return bookOrderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBookOrder(@RequestBody BookOrderDTO bookOrderDTO) {
        logger.info("Received BookOrderDTO: {}", bookOrderDTO);

        // Step 1: Check if the book exists
        Optional<Book> bookOptional = bookRepository.findById(bookOrderDTO.getBookId());
        if (bookOptional.isEmpty()) {
            logger.error("Book with ID {} not found", bookOrderDTO.getBookId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + bookOrderDTO.getBookId() + " not found.");
        }

        // Step 2: Check if the shopping cart exists
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(bookOrderDTO.getShoppingCartId());
        if (shoppingCartOptional.isEmpty()) {
            logger.error("Shopping cart with ID {} not found", bookOrderDTO.getShoppingCartId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart with ID " + bookOrderDTO.getShoppingCartId() + " not found.");
        }

        // Step 3: Create the book order and link it to the book and shopping cart
        Book book = bookOptional.get();
        ShoppingCart shoppingCart = shoppingCartOptional.get();
        BookOrder bookOrder = new BookOrder();
        bookOrder.setBook(book);
        bookOrder.setShoppingCart(shoppingCart);

        // Step 4: Save the book order
        BookOrder savedBookOrder = bookOrderRepository.save(bookOrder);
        logger.info("BookOrder saved: {}", savedBookOrder);

        // Return the created book order
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookOrder);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateBookOrder(@PathVariable Long id, @RequestBody BookOrderDTO updatedBookOrderDTO) {
        // Step 1: Check if the book order exists
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(id);
        if (bookOrderOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book order with ID " + id + " not found.");
        }

        // Step 2: Get the existing book order
        BookOrder bookOrder = bookOrderOptional.get();

        // Step 3: Update the bookId or shoppingCartId if provided in the request body
        if (updatedBookOrderDTO.getBookId() != null) {
            Optional<Book> bookOptional = bookRepository.findById(updatedBookOrderDTO.getBookId());
            if (bookOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + updatedBookOrderDTO.getBookId() + " not found.");
            }
            bookOrder.setBook(bookOptional.get());
        }
        if (updatedBookOrderDTO.getShoppingCartId() != null) {
            Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(updatedBookOrderDTO.getShoppingCartId());
            if (shoppingCartOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart with ID " + updatedBookOrderDTO.getShoppingCartId() + " not found.");
            }
            bookOrder.setShoppingCart(shoppingCartOptional.get());
        }

        // Step 4: Save the updated book order
        BookOrder updatedOrder = bookOrderRepository.save(bookOrder);

        // Return the updated book order
        return ResponseEntity.ok(updatedOrder);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookOrder(@PathVariable Long id) {
        // Step 1: Check if the book order exists
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(id);
        if (bookOrderOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book order with ID " + id + " not found.");
        }

        // Step 2: Delete the book order
        bookOrderRepository.deleteById(id);

        // Return a success message
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/by-shopping-cart/{shoppingCartId}")
    public ResponseEntity<?> deleteBookOrderByShoppingCartId(@PathVariable Long shoppingCartId) {
        // Step 1: Check if any book order exists for the specified shopping cart
        List<BookOrder> bookOrders = bookOrderRepository.findByShoppingCartId(shoppingCartId);
        if (bookOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book orders found for shopping cart with ID " + shoppingCartId);
        }

        // Step 2: Delete all book orders associated with the shopping cart
        bookOrderRepository.deleteAll(bookOrders);

        // Return a success message
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/by-book/{bookId}")
    public ResponseEntity<?> deleteBookOrderByBookId(@PathVariable Long bookId) {
        // Step 1: Check if any book order exists for the specified book
        List<BookOrder> bookOrders = bookOrderRepository.findByBookId(bookId);
        if (bookOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book orders found for book with ID " + bookId);
        }

        // Step 2: Delete all book orders associated with the book
        bookOrderRepository.deleteAll(bookOrders);

        // Return a success message
        return ResponseEntity.noContent().build();
    }
}

