package com.kevin.emazon_cart.infraestructure.adapter;

import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.domain.spi.external.IConnectionStockPort;
import com.kevin.emazon_cart.infraestructure.feign.service.StockFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeignStockAdapter implements IConnectionStockPort {

    private final StockFeignClient stockFeignClient;

    @Override
    public boolean existsItemById(Long itemId) {
        return stockFeignClient.existItemById(itemId);
    }

    @Override
    public boolean isEnoughStock(Long itemId,Long itemQuantity) {
        return stockFeignClient.isEnoughInStock(itemId, itemQuantity);
    }

    @Override
    public boolean validateCategoryLimit(List<Long> itemsIds) {
        return stockFeignClient.validateCategoryLimit(itemsIds);
    }

    @Override
    public List<ItemCartResponse> findAllProductsInCart(ItemCartRequest itemCartRequest) {
        return stockFeignClient.findAllProductsInCart(itemCartRequest);
    }

    @Override
    public void decreaseQuantityInStock(Long itemId, Long quantity) {
        stockFeignClient.reduceQuantityInStock(itemId, quantity);
    }


}
