package com.kevin.emazon_cart.domain.spi.externalservices;


import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;

import java.util.List;

public interface IConnectionStockPort {
    boolean existsItemById(Long itemId);

    boolean isEnoughStock(Long itemId,Long itemQuantity);
    boolean validateCategoryLimit(List<Long> itemsIds);

    List<ItemCartResponse>findAllProductsInCart(ItemCartRequest itemCartRequest);

}
