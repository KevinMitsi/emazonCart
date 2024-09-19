package com.kevin.emazon_cart.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCartResponse {
    private Long itemId;
    private String itemName;
    private Long brandId;
    private String quantity;

    public ItemCartResponse(Long itemId, String itemName, Long brandId, String quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.brandId = brandId;
        this.quantity = quantity;
    }

    public ItemCartResponse() {
        //For frameworks etc
    }
}