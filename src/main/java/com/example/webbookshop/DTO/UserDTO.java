package com.example.webbookshop.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Double balance;
    private Boolean adminAccess;
}
