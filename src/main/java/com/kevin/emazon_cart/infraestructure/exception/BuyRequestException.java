package com.kevin.emazon_cart.infraestructure.exception;

public class BuyRequestException extends RuntimeException{
    public BuyRequestException(String message) {
        super(message);
    }
}
