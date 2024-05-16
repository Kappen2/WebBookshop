package com.example.webbookshop.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private Double price = 0.0; // Default value is 0
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookOrder> bookOrders = new ArrayList<>();

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", bookOrders=" + bookOrders.size() +
                '}';
    }

    // Add this method to calculate the total price based on associated books
    public Double calculateTotalPrice() {
        Double totalPrice = 0.0;
        if (bookOrders != null) {
            for (BookOrder bookOrder : bookOrders) {
                totalPrice += bookOrder.getBook().getPrice();
            }
        }
        return totalPrice;
    }
}
