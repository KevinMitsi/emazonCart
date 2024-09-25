package com.kevin.emazon_cart.application.handler;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import org.springframework.data.domain.Page;


public interface ICartHandler {
    String addItemToCart(CartDto cartDto);

    void deleteByItemId(Long itemId, Long userId);

    Page<ItemCartResponse> findAllProductsInCart(Long category, Long brand,
                                                 String orderingMethod, Integer pageNumber, Integer pageSize);
    void buy();
}
