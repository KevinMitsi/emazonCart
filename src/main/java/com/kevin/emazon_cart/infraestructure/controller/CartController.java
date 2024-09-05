package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    public static final String ADDED_TO_CART_MESSAGE = "Felicidades ha accedido a añadir carrito";
    public static final String ROLE_CLIENTE = "ROLE_CLIENTE";
    public static final String ROLE_ADMIN = "ROLE_ADMINISTRADOR";
    private final ICartHandler cartHandler;

    @Secured({ROLE_CLIENTE, ROLE_ADMIN})
    @GetMapping("add/{userId}/{itemId}")
    public ResponseEntity<String> addToCart(@PathVariable String userId, @PathVariable String itemId){
        return ResponseEntity.status(200).body(ADDED_TO_CART_MESSAGE);
    }
}
