package com.kevin.emazon_cart.infraestructure.feign.service;

import com.kevin.emazon_cart.infraestructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "STOCK_API", url = "http://localhost:8082/api/v1", configuration = FeignConfig.class)
public interface StockFeignClient {
    @GetMapping("/item/exist/{id}")
    boolean existItemById(@PathVariable Long id);

    @GetMapping("/item/getTotal/{id}")
    Long getAmountOfItemsInStock(@PathVariable Long id);

}
