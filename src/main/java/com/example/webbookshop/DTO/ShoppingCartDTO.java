package com.example.webbookshop.DTO;

import lombok.Data;

@Data
public class ShoppingCartDTO {
    private Long userId;
    private Long bookOrderId;
    private Double price;
}