package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;

import java.time.Instant;
import java.util.Date;

public class CartUseCase implements ICartServicePort {
    private final ICartPersistentPort cartPersistentPort;

    public CartUseCase(ICartPersistentPort cartPersistentPort) {
        this.cartPersistentPort = cartPersistentPort;
    }


    @Override
    public void addItemToCart(Cart cart) {
        prepareCart(cart);
        cartPersistentPort.addItemToCart(cart);
    }

    private static void prepareCart(Cart cart) {
        if (cart.getCreationDate() == null){
            cart.setCreationDate(Date.from(Instant.now()));
        }
        cart.setUpdateDate(Date.from(Instant.now()));
    }
}
