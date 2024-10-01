package com.kevin.emazon_cart.infraestructure.feign.service;

import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.infraestructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "stock-api", url = "http://localhost:8082/api/v1/item/", configuration = FeignConfig.class)
public interface StockFeignClient {
    @GetMapping("exist/{id}")
    boolean existItemById(@PathVariable Long id);

    @GetMapping("isEnough/{id}/{quantity}")
    boolean isEnoughInStock(@PathVariable Long id, @PathVariable Long quantity);
    @PostMapping("validateCategoryLimit")
    boolean validateCategoryLimit(@RequestBody List<Long> itemIds);

    @PostMapping("itemCarts")
    List<ItemCartResponse> findAllProductsInCart(@RequestBody ItemCartRequest itemCartRequest);

    @PatchMapping("reduceQuantity/{itemID}/{quantity}")
    void reduceQuantityInStock(@PathVariable Long itemID, @PathVariable Long quantity);

}
