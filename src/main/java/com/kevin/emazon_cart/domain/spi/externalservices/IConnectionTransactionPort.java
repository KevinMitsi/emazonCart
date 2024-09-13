package com.kevin.emazon_cart.domain.spi.externalservices;

import java.util.Date;

public interface IConnectionTransactionPort {
    Date getItemSupplyDate(Long id);
}
