package com.kevin.emazon_cart.domain.spi;

import com.kevin.emazon_cart.domain.model.Cart;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICartPersistentPort {
    void addItemToCart(Cart cart);

    List<Long>getItemsInUserCart(Long userId);

    void updateItemQuantity(Cart cart);

    Long getItemQuantityByItemId(Long itemId);
    Optional<Date> findDateByItemId(Long  itemId);

    void deleteByItemId(Long itemId, Long userId);
    List<Cart> findAllProductsInCart(Long userId);

    void saveAll(List<Cart> products);
}
