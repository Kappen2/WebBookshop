package com.example.webbookshop.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double price;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    private List<BookOrder> bookOrders;

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
