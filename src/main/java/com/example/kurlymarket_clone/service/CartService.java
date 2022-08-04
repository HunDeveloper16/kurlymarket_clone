package com.example.kurlymarket_clone.service;

import com.example.kurlymarket_clone.exception.CustomException;

import com.example.kurlymarket_clone.model.Cart;
import com.example.kurlymarket_clone.model.CartItem;
import com.example.kurlymarket_clone.model.Post;
import com.example.kurlymarket_clone.model.User;
import com.example.kurlymarket_clone.repository.CartItemRepository;
import com.example.kurlymarket_clone.repository.CartRepository;
import com.example.kurlymarket_clone.repository.PostRepository;
import com.example.kurlymarket_clone.repository.UserRepository;
import com.example.kurlymarket_clone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.kurlymarket_clone.exception.ErrorCode.*;

//import static com.sparta.springweb.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CartService { //장바구니

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CartItemRepository cartItemRepository;

    public CartItem postItem(Long postId, UserDetailsImpl userDetails) {
        //  유저확인
        if (userDetails.getUser() == null){
           throw new CustomException(NOT_FOUND_USER);
        }

        Cart cart = cartRepository.findByUser(userDetails.getUser());
        List<CartItem> cartItemList =new ArrayList<>();
        // 카트가 없을 경우 생성
        if (cart == null){
            cart = Cart.builder()
                    .user(userDetails.getUser())
                    .username(userDetails.getUsername())
                    .deliveryFee(0)
                    .totalPrice(0)
                    .posts(cartItemList)
                    .build();
            cartRepository.save(cart);
        }

        Post post = postRepository.findPostById(postId);
        //해당 물건이 존재하지 않을 시
        if (post == null){
            throw new CustomException(NOT_FOUND_POST);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndPostId(cart.getId(), postId);

        //장바구니에 물건 추가
        if (cartItem == null){
            cartItem = CartItem.builder()
                    .cart(cart)
                    .title(post.getTitle())
                    .imageUrl(post.getFile().getFileUrl())
                    .price(post.getPrice())
                    .build();
            cartItemRepository.save(cartItem);
        }else{
            //물건 추가 구매 기능
        }

        return cartItem;
    }

    // 장바구니 조회
    @Transactional
    public Cart getCart(User user) {
        //  유저확인
        if (user == null){
            throw new CustomException(NOT_FOUND_USER);
        }
        Cart cart = cartRepository.findByUser(user);
        List<CartItem> cartItemList =new ArrayList<>();

        //장바구니가 없는 경우 추가
        if (cart == null){
            cart = Cart.builder()
                    .user(user)
                    .username(user.getUsername())
                    .deliveryFee(0)
                    .totalPrice(0)
                    .posts(cartItemList)
                    .build();
            cartRepository.save(cart);
            return cart;
        }

        int totalPrice = 0;
        int deliveryFee = 0;

        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cart);

        //만약 장바구니안에 물건이 있을 경우, 없으면 0원
        if (!cartItems.isEmpty()){
            deliveryFee = 3000;
        }

        for (CartItem cartItem : cartItems){
            cartItemList.add(cartItem);
            totalPrice += cartItem.getPrice();
        }

        // 총 금액이 2만원이 넘여갈 경우 배달료 0원
        if (totalPrice > 20000){
            deliveryFee = 0;
        }

        cart.setTotalPrice(totalPrice);
        cart.setDeliveryFee(deliveryFee);
        cart.setPosts(cartItemList);

        return cart;
    }

    //특정 물건 삭제
    public Cart deleteItem(Long cartItemId, User user) {
        //  유저확인
        if (user == null){
            throw new CustomException(NOT_FOUND_USER);
        }
        //장바구니 아이디가 정확한지 확인
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        Cart cart = cartRepository.findByUser(user);
        //  장바구니 확인
        if (cart == null){
            throw new CustomException(NOT_FOUND_ITEM);
        }

        //상속 받고 있는 데이터 부터 삭제
        List<CartItem> items =  cart.getPosts();
        for (int i = 0; i < items.size(); i++) {
            if (Objects.equals(items.get(i).getId(), cartItemId)){
                items.remove(i);
                break;
            }
        }
        cart.setPosts(items);

        cartItemRepository.deleteById(cartItemId);

        return cart;
    }

    //장바구니 전체 삭제
    public Cart deleteCart(User user) {
        //  유저확인
        if (user == null){
            throw new CustomException(NOT_FOUND_USER);
        }

        Cart cart = cartRepository.findByUser(user);
        //  장바구니 확인
        if (cart == null){
            throw new CustomException(NOT_FOUND_ITEM);
        }

        List<CartItem> items =  cart.getPosts();
        //  장바구니 물건 확인
        if (items == null){
           throw new CustomException(NOT_FOUND_ITEM);
        }

        //장바구니에 있는 물건 비우기
        cart.setPosts(null);

        //데이터 삭제
        for (CartItem item : Objects.requireNonNull(items)){
            cartItemRepository.deleteById(item.getId());
        }

        return cart;
    }
}
