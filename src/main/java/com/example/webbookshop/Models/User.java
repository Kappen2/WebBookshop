package com.example.webbookshop.Models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    int id;
    String email;
    String password;
    String firstName;
    String lastName;
    double balance;
    boolean adminAccess = false;
}
