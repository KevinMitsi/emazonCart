package com.kevin.emazon_cart.infraestructure.feign.service;

import com.kevin.emazon_cart.domain.model.SaleRequest;
import com.kevin.emazon_cart.infraestructure.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@FeignClient(name = "transaction-api", url = "http://localhost:8084/api/v1/", configuration = FeignConfig.class)
public interface TransactionFeignClient {

    @GetMapping("supply/supplyDate/{id}")
    Date getSupplyItemDate(@PathVariable Long id);

    @PostMapping("sale/buy")
    void sendBuyRequest(@RequestBody SaleRequest saleRequest);

}
