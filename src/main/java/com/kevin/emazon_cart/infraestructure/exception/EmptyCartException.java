package com.kevin.emazon_cart.infraestructure.exception;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message) {
        super(message);
    }
}
