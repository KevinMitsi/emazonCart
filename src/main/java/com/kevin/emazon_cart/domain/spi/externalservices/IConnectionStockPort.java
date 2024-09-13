package com.kevin.emazon_cart.domain.spi.externalservices;


import java.util.List;

public interface IConnectionStockPort {
    boolean existsItemById(Long itemId);

    boolean isEnoughStock(Long itemId,Long itemQuantity);
    boolean validateCategoryLimit(List<Long> itemsIds);

}
