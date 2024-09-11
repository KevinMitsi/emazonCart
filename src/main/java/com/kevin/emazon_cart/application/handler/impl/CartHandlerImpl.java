package com.kevin.emazon_cart.application.handler.impl;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.application.handler.ICartHandler;
import com.kevin.emazon_cart.application.mapper.ICartDtoMapper;
import com.kevin.emazon_cart.domain.api.ICartServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandlerImpl implements ICartHandler {
    private final ICartServicePort cartServicePort;
    private final ICartDtoMapper cartDtoMapper;

    @Override
    public void addItemToCart(CartDto cartDto) {
        cartServicePort.addItemToCart(cartDtoMapper.cartDtoToCart(cartDto));
    }
}
