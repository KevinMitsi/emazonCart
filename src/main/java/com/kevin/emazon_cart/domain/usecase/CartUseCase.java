package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;

public class CartUseCase implements ICartServicePort {
    private final ICartPersistentPort cartPersistentPort;

    public CartUseCase(ICartPersistentPort cartPersistentPort) {
        this.cartPersistentPort = cartPersistentPort;
    }


    @Override
    public void addItemToCart() {
        cartPersistentPort.addItemToCart();
    }
}
