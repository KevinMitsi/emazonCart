package com.kevin.emazon_cart.infraestructure.adapter;

import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionTransactionPort;
import com.kevin.emazon_cart.infraestructure.feign.service.TransactionFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeignTransactionAdapter implements IConnectionTransactionPort {

    private final TransactionFeignClient transactionFeignClient;
    @Override
    public Date getItemSupplyDate(Long id) {
        return transactionFeignClient.getSupplyItemDate(id);
    }
}
