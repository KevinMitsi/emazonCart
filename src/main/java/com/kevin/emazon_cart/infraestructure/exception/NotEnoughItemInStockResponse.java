package com.kevin.emazon_cart.infraestructure.exception;

public class NotEnoughItemInStockResponse extends RuntimeException{
    public NotEnoughItemInStockResponse(String message) {
        super(message);
    }
}
