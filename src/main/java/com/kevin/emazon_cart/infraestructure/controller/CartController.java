package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@Validated
public class CartController {
    public static final String ADDED_TO_CART_MESSAGE = "Felicidades usuario ha accedido a añadir carrito usuario: ";
    public static final String ROLE_CLIENT = "ROLE_CLIENTE";


    private final ICartHandler cartHandler;

    @Secured(ROLE_CLIENT)
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto){
        cartDto.setUserId((Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        cartHandler.addItemToCart(cartDto);
        return ResponseEntity.status(200).body(ADDED_TO_CART_MESSAGE+cartDto.getUserId());
    }



}
