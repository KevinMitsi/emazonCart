package com.kevin.emazon_cart.infraestructure.mapper;

import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.infraestructure.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartEntityMapper {

    Cart cartEntityToCart(CartEntity cartEntity);

    CartEntity cartToCartEntity(Cart cart);
}
