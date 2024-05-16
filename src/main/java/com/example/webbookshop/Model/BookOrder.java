package com.example.webbookshop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @JsonBackReference
    private ShoppingCart shoppingCart;

    @Override
    public String toString() {
        return "BookOrder{" +
                "id=" + id +
                ", shoppingCartId=" + (shoppingCart != null ? shoppingCart.getId() : null) +
                '}';
    }
}
