package com.kevin.emazon_cart.infraestructure.exception;

public class ItemIsNotInCartException extends RuntimeException{
    public ItemIsNotInCartException(String message) {
        super(message);
    }
}
