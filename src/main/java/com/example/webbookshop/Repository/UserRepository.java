package com.example.webbookshop.Repository;

import com.example.webbookshop.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
