package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.application.handler.ICartHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
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
    public static final String ROLE_CLIENT = "ROLE_CLIENTE";


    private final ICartHandler cartHandler;

    @PostMapping("/add")
    @Secured(ROLE_CLIENT)
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartDto cartDto){
        cartDto.setUserId((Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return ResponseEntity.status(201).body(cartHandler.addItemToCart(cartDto));
    }

    @DeleteMapping("delete/byItemId/{itemId}")
    @Secured(ROLE_CLIENT)
    public ResponseEntity<String> deleteItemFromCart(@PathVariable Long itemId){
        cartHandler.deleteByItemId(itemId, (Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return ResponseEntity.status(200).body("deleted");
    }



}
