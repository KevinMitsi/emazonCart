package com.kevin.emazon_cart.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemDetails {
    private Long itemId;

    private Long quantity;

    public SaleItemDetails() {
    }

    public SaleItemDetails(Long itemId, Long quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
