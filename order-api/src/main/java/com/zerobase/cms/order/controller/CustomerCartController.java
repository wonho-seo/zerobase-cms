package com.zerobase.cms.order.controller;

import com.zerobase.cms.config.JwtAuthenticationProvider;
import com.zerobase.cms.order.application.CartApplication;
import com.zerobase.cms.order.application.OrderApplication;
import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

    private final CartApplication cartApplication;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final OrderApplication orderApplication;

    @PostMapping
    public ResponseEntity<Cart> addCart(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody AddProductCartForm form) {
        return ResponseEntity.ok(
            cartApplication.addCart(jwtAuthenticationProvider.getUserVo(token).getId(), form));
    }

    @GetMapping
    public ResponseEntity<Cart> showCart(
        @RequestHeader(name = "X-AUTH-TOKEN") String token){
        return ResponseEntity.ok(
            cartApplication.getCart(jwtAuthenticationProvider.getUserVo(token).getId())
        );
    }

    @PutMapping
    public ResponseEntity<Cart> updateCart(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody Cart cart
    ){
        return ResponseEntity.ok(
            cartApplication.updateCart(jwtAuthenticationProvider.getUserVo(token).getId(), cart)
        );
    }

    @PostMapping("/order")
    public ResponseEntity<String> order(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody Cart cart){
        Cart orderCart = orderApplication.order(token, cart);
        return ResponseEntity.ok(orderApplication.sendOrderMail(token, orderCart));
    }

}
