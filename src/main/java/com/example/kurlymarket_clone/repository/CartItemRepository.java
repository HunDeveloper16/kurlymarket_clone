package com.example.kurlymarket_clone.repository;

import com.example.kurlymarket_clone.model.Cart;
import com.example.kurlymarket_clone.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemsByCart(Cart cart);

    CartItem findByCartIdAndPostId(Long cartId, Long postId);

    void deleteAllByCart(Cart cart);
}
