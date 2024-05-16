package com.example.webbookshop.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;
    String password;
    String firstName;
    String lastName;
    Double balance;
    boolean adminAccess = false;
}
