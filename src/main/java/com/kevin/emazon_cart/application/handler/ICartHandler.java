package com.kevin.emazon_cart.application.handler;

import com.kevin.emazon_cart.application.dto.CartDto;

public interface ICartHandler {
    String addItemToCart(CartDto cartDto);

    void deleteByItemId(Long itemId, Long userId);
}
