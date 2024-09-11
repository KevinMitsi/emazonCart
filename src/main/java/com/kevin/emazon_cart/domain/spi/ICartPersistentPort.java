package com.kevin.emazon_cart.domain.spi;

import com.kevin.emazon_cart.domain.model.Cart;

public interface ICartPersistentPort {
    void addItemToCart(Cart cart);
}
