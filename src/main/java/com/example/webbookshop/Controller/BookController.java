package com.example.webbookshop.Controller;

import com.example.webbookshop.DTO.BookDTO;
import com.example.webbookshop.Model.Book;
import com.example.webbookshop.Repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/books")
public class BookController {

    private final BookRepository repo;

    BookController(BookRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable long id){
        Optional<Book> bookOptional = repo.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            return ResponseEntity.ok().body(book);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author/{author}")
    public List<Book> findByAuthor(@PathVariable String author){
        return repo.findByAuthor(author);
    }

    @GetMapping("/title/{title}")
    public List<Book> findByTitle(@PathVariable String title){return repo.findByTitle(title);}

    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBooks(@RequestBody BookDTO bookDTO){
        // Convert BookDTO to Book entity
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());

        // Save the Book entity
        Book savedBook = repo.save(book);

        // Convert saved Book entity back to BookDTO and return
        BookDTO savedBookDTO = new BookDTO();
        savedBookDTO.setTitle(savedBook.getTitle());
        savedBookDTO.setAuthor(savedBook.getAuthor());
        savedBookDTO.setPrice(savedBook.getPrice());

        return ResponseEntity.ok().body(savedBookDTO);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook){
        Optional<Book> bookOptional = repo.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            if(updatedBook.getTitle() != null) {
                book.setTitle(updatedBook.getTitle());
            }
            if(updatedBook.getAuthor() != null) {
                book.setAuthor(updatedBook.getAuthor());
            }
            if(updatedBook.getPrice() != 0.0) {
                book.setPrice(updatedBook.getPrice());
            }
            repo.save(book);
            return ResponseEntity.ok().body(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public List<Book> deleteById(@PathVariable Long id) {
        repo.deleteById(id);
        return repo.findAll();
    }
}
