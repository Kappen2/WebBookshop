package com.example.webbookshop.Model;

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
    Long id;
    double price;
    @OneToOne
    private User user;
}
