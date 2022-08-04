package com.example.kurlymarket_clone.repository;

import com.example.kurlymarket_clone.model.Cart;
import com.example.kurlymarket_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
