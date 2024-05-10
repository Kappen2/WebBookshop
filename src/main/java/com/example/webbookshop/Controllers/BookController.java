package com.example.webbookshop.Controllers;

import com.example.webbookshop.Models.Book;
import com.example.webbookshop.Repositories.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository repo;

    BookController(BookRepository repo){
        this.repo = repo;
    }

    @RequestMapping("/books")
    public List<Book> getAllBooks(){
        return repo.findAll();
    }

    @RequestMapping("/books/{id}")
    public Book findById(@PathVariable long id){
        return repo.findById(id).get();
    }

    @RequestMapping("/books/{author}/author")
    public List<Book> findByAuthor(@PathVariable String author){
        return repo.findByAuthor(author);
    }

    @PostMapping("/books/add")
    public List<Book> addBooks(@RequestBody Book b){
        repo.save(b);
        return repo.findAll();
    }

    @RequestMapping("/books/{id}/delete")
    public List<Book> deleteById(@PathVariable long id) {
        repo.deleteById(id);
        return repo.findAll();
    }

    @RequestMapping("/books/{id}/buy")
    public String buyById(@PathVariable long id,
                          @RequestParam String title,
                          @RequestParam String author,
                          @RequestParam double price) {

        return "Du har köpt " + title + " av " + author + " för " + price + " kr.";
    }


}
