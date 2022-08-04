package com.example.kurlymarket_clone.repository;


import com.example.kurlymarket_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findAllByUsername(String username);
}