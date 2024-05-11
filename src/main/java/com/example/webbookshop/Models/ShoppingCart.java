package com.example.webbookshop.Models;

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
}
