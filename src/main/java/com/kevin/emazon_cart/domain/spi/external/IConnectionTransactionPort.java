package com.kevin.emazon_cart.domain.spi.external;

import com.kevin.emazon_cart.domain.model.SaleRequest;

import java.util.Date;

public interface IConnectionTransactionPort {
    Date getItemSupplyDate(Long id);

    void createSaleRequest(SaleRequest saleRequest);
}
