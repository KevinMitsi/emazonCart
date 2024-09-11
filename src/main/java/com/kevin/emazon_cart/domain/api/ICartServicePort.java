package com.kevin.emazon_cart.domain.api;

import com.kevin.emazon_cart.domain.model.Cart;

public interface ICartServicePort {
    void addItemToCart(Cart cart);
}
