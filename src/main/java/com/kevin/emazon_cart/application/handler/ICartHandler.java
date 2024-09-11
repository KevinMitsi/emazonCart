package com.kevin.emazon_cart.application.handler;

import com.kevin.emazon_cart.application.dto.CartDto;

public interface ICartHandler {
    void addItemToCart(CartDto cartDto);
}
