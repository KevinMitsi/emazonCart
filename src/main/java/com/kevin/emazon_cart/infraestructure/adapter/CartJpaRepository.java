package com.kevin.emazon_cart.infraestructure.adapter;

import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.infraestructure.entity.CartEntity;
import com.kevin.emazon_cart.infraestructure.mapper.ICartEntityMapper;
import com.kevin.emazon_cart.infraestructure.repository.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CartJpaRepository implements ICartPersistentPort {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    @Override
    @Transactional
    public void addItemToCart(Cart cart) {
        cartRepository.save(cartEntityMapper.cartToCartEntity(cart));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getItemsInUserCart(Long userId) {
        return cartRepository.itemsInUserCart(userId);
    }

    @Override
    @Transactional
    public void updateItemQuantity(Cart cart) {
        CartEntity cartEntity = cartRepository.findByItemId(cart.getItemId()).orElseThrow();
        cartEntity.setQuantity(cart.getQuantity());
        cartEntity.setUpdateDate(cart.getUpdateDate());
        cartRepository.save(cartEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getItemQuantityByItemId(Long itemId) {
        return cartRepository.findItemQuantityByItemId(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Date> findDateByItemId(Long itemId) {
        return cartRepository.findDateByItemId(itemId);
    }

    @Override
    @Transactional
    public void deleteByItemId(Long itemId, Long userId) {
        cartRepository.deleteByItemIdAndUserId(itemId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> findAllProductsInCart(Long userId) {
        return cartRepository.findAllByUserId(userId)
                .stream()
                .map(cartEntityMapper::cartEntityToCart).toList();
    }

    @Override
    @Transactional
    public void saveAll(List<Cart> products) {
        cartRepository.saveAll(products.stream().map(cartEntityMapper::cartToCartEntity).toList());
    }



}
