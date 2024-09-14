package com.kevin.emazon_cart.domain.api;

import com.kevin.emazon_cart.domain.model.Cart;

import java.util.List;

public interface ICartServicePort {
    String addItemToCart(Cart cart);
    List<Long> getItemsInUserCart(Long userId);

    void deleteByItemId(Long itemId);
}
