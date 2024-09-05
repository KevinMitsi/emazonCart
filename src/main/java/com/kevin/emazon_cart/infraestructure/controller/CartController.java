package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@Setter
public class CartController {
    private Long userId;
    public static final String ADDED_TO_CART_MESSAGE = "Felicidades usuario ha accedido a añadir carrito usuario: ";
    public static final String ROLE_CLIENTE = "ROLE_CLIENTE";
    public static final String ROLE_ADMIN = "ROLE_ADMINISTRADOR";


    private final ICartHandler cartHandler;

    @Secured({ROLE_CLIENTE, ROLE_ADMIN})
    @GetMapping("add/{itemId}")
    public ResponseEntity<String> addToCart(@PathVariable Long itemId){
        setUserId((Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        cartHandler.addItemToCart(userId, itemId);
        return ResponseEntity.status(200).body(ADDED_TO_CART_MESSAGE+userId);
    }

}
