package com.example.webbookshop.Repositories;

import com.example.webbookshop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
