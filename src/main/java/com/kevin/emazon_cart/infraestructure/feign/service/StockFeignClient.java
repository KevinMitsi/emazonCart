package com.kevin.emazon_cart.infraestructure.feign.service;

import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.infraestructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "stock-api", url = "http://localhost:8082/api/v1", configuration = FeignConfig.class)
public interface StockFeignClient {
    @GetMapping("/item/exist/{id}")
    boolean existItemById(@PathVariable Long id);

    @GetMapping("/item/isEnough/{id}/{quantity}")
    boolean isEnoughInStock(@PathVariable Long id, @PathVariable Long quantity);
    @PostMapping("/item/validateCategoryLimit")
    boolean validateCategoryLimit(@RequestBody List<Long> itemIds);

    @PostMapping("/item/itemCarts")
    List<ItemCartResponse> findAllProductsInCart(@RequestBody ItemCartRequest itemCartRequest);

}
