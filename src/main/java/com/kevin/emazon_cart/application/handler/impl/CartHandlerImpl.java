package com.kevin.emazon_cart.application.handler.impl;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.application.handler.ICartHandler;
import com.kevin.emazon_cart.application.mapper.ICartDtoMapper;
import com.kevin.emazon_cart.application.util.ListToPageConverter;
import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandlerImpl implements ICartHandler {
    private final ICartServicePort cartServicePort;
    private final ICartDtoMapper cartDtoMapper;

    @Override
    public String addItemToCart(CartDto cartDto) {
       return cartServicePort.addItemToCart(cartDtoMapper.cartDtoToCart(cartDto));
    }

    @Override
    public void deleteByItemId(Long itemId, Long userId) {
        cartServicePort.deleteByItemId(itemId,userId);
    }

    @Override
    public Page<ItemCartResponse> findAllProductsInCart(Long userId, Long category, Long brand,
                                                        String orderingMethod, Integer pageNumber, Integer pageSize) {

        return ListToPageConverter
                .convertListIntoPage(cartServicePort.findAllProductsInCart(userId, category, brand),
                        pageNumber,
                        pageSize,
                        orderingMethod);
    }
}
