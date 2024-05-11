package com.example.webbookshop.Models;

import jakarta.persistence.*;
import lombok.Data;

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
}
