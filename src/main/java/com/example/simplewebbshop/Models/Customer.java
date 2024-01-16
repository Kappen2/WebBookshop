package com.example.simplewebbshop.Models;

import lombok.Data;

@Data
public class Customer {
    long id;
    String name;
    String password;
    long balance;
}
