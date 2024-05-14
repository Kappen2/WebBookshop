package com.example.webbookshop.Repositories;

import com.example.webbookshop.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(long userId);
}
