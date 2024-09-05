package com.kevin.emazon_cart.application.mapper;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.domain.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartDtoMapper {
    Cart cartDtoToCart(CartDto cartDto);

    CartDto cartToCartDto(Cart cart);
}
