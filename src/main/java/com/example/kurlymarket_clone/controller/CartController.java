package com.example.kurlymarket_clone.controller;


import com.example.kurlymarket_clone.dto.CartResponseDto;
import com.example.kurlymarket_clone.model.Cart;
import com.example.kurlymarket_clone.security.UserDetailsImpl;
import com.example.kurlymarket_clone.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CartController {
    private final CartService cartService;

    // 장바구니 물건 추가
    @PostMapping("/api/post/item/{postId}")
    public ResponseEntity getItem(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //페이지 리턴 response entity
        cartService.postItem(postId, userDetails);

        return new ResponseEntity("장바구니에 추가되었습니다",HttpStatus.OK);
    }

    // 장바구니 조회 가능
    @GetMapping("/api/cart")
    public ResponseEntity getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cart cart = cartService.getCart(userDetails.getUser());
        CartResponseDto responseDto = new CartResponseDto(cart);
        return new ResponseEntity(responseDto,HttpStatus.OK);
    }

    // 장바구니 특정 물건 삭제
    @DeleteMapping("/api/cart/item/{cartItemId}")
    public ResponseEntity deleteItem(@PathVariable Long cartItemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.deleteItem(cartItemId, userDetails.getUser());
        return new ResponseEntity("정상적으로 물건이 삭제되었습니다.",HttpStatus.OK);

    }
    // 장바구니 전체 삭제
    @DeleteMapping("/api/cart")
    public ResponseEntity deleteCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.deleteCart(userDetails.getUser());
        return new ResponseEntity("정상적으로 장바구니가 비워졌습니다.",HttpStatus.OK);
    }
}
