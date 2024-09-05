package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartHandler cartHandler;
}
