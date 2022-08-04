package com.example.kurlymarket_clone.dto;

import com.example.kurlymarket_clone.model.CartItem;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
public class CartRequestDto {
    private String username;
    private List<CartItem> items;

    //장바구니
    public CartRequestDto(List<CartItem> items, UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.items = items;
    }
}
