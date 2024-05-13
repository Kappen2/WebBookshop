package com.example.webbookshop.Controllers;

import com.example.webbookshop.Models.Book;
import com.example.webbookshop.Repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository repo;

    BookController(BookRepository repo){
        this.repo = repo;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return repo.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable long id){
        Optional<Book> bookOptional = repo.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            return ResponseEntity.ok().body(book);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/books/author/{author}")
    public List<Book> findByAuthor(@PathVariable String author){
        return repo.findByAuthor(author);
    }

    @GetMapping("/books/title/{title}")
    public List<Book> findByTitle(@PathVariable String title){return repo.findByTitle(title);}

    @PostMapping("/books/add")
    public Book addBooks(@RequestBody Book b){
        return repo.save(b);
    }

    @PatchMapping("/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book updatedBook){
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


    @DeleteMapping("/books/delete/{id}")
    public List<Book> deleteById(@PathVariable long id) {
        repo.deleteById(id);
        return repo.findAll();
    }
    //Method for adding book to BookOrder
    /*@RequestMapping("/books/buy/{id}")
    public Optional<Book> buyById(@PathVariable long id) {
        Optional<Book> book = repo.findById(id);
        return book;
    }*/
}
