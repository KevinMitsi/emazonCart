package com.kevin.emazon_cart.infraestructure.exception;
public class FeignRequestException extends RuntimeException{
    public FeignRequestException(String message) {
        super(message);
    }
}
