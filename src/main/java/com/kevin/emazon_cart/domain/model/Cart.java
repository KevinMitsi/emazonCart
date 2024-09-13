package com.kevin.emazon_cart.domain.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Cart {
    private Long id;
    private Long itemId;

    private Long userId;
    private Long quantity;
    private Date updateDate;
    private Date creationDate;

    public Cart() {
        //FOR MAPPING AND FRAMEWORKS
    }

    public Cart(Long id, Long itemId, Long userId, Long quantity, Date updateDate, Date creationDate) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.creationDate = creationDate;
    }

}
