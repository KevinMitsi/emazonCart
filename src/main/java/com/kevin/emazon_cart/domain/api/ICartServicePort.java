package com.kevin.emazon_cart.domain.api;

import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;

import java.util.List;

public interface ICartServicePort {
    String addItemToCart(Cart cart);
    List<Long> getItemsIdsInUserCart(Long userId);

    void deleteByItemId(Long itemId, Long userId);

    List<ItemCartResponse> findAllProductsInCart(Long userId, Long category, Long brand);
}
