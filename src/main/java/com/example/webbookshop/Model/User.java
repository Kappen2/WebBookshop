package com.example.webbookshop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String username;
    String password;
    String firstName;
    String lastName;
    Double balance;
    boolean adminAccess = false;
}