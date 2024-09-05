package com.kevin.emazon_cart.domain.model;


import java.util.Date;

public class Cart {
    private Long id;
    private Long itemId;

    private Long userId;
    private Double quantity;
    private Date updateDate;
    private Date creationDate;

    public Cart() {
        //FOR MAPPING AND FRAMEWORKS
    }

    public Cart(Long id, Long itemId, Long userId, Double quantity, Date updateDate, Date creationDate) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
