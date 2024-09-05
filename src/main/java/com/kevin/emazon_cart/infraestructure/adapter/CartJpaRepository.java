package com.kevin.emazon_cart.infraestructure.adapter;

import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.infraestructure.mapper.ICartEntityMapper;
import com.kevin.emazon_cart.infraestructure.repository.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartJpaRepository implements ICartPersistentPort {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    @Override
    public void addItemToCart() {
        //not yet
    }
}
