package com.example.webbookshop.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookOrder {

    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private ShoppingCart shoppingCart;
}
