package com.example.webbookshop.Models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    int id;
    double price;
    @OneToOne
    private User user;

    //Möjlig lösning föreslagen av ChatGPT. Bör initieras i ordningen Book, User, Shoppingcart, BookOrder.
    @PostConstruct
    public void init() {
        // Initialization logic for ShoppingCart entity
    }
}
