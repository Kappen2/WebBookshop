package com.example.webbookshop.Models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.DependsOn;

@Data
@Entity
public class BookOrder {

    @Id
    @GeneratedValue
    int id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private ShoppingCart shoppingCart;

    //Möjlig lösning föreslagen av ChatGPT. Bör initieras i ordningen Book, User, Shoppingcart, BookOrder.
    @PostConstruct
    public void init() {
        // Initialization logic for BookOrder entity
    }
}
