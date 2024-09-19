package com.kevin.emazon_cart.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemCartRequest {
    private List<Long> itemIds;

    private Long categoryToOrder;
    private Long brandToOrder;

    public ItemCartRequest(List<Long> itemIds, Long categoryToOrder, Long brandToOrder) {
        this.itemIds = itemIds;
        this.categoryToOrder = categoryToOrder;
        this.brandToOrder = brandToOrder;
    }

    public ItemCartRequest() {
        //for frameworks etc
    }
}