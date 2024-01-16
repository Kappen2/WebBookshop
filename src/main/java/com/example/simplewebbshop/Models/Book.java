package com.example.simplewebbshop.Models;

import lombok.Data;

@Data
public class Book {
    long id;
    String title;
    String author;
    long price;
}
