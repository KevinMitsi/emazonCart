package com.kevin.emazon_cart.infraestructure.exception;
public class FeignServerException extends RuntimeException{
    public FeignServerException(String message) {
        super(message);
    }
}
