package com.kevin.emazon_cart.infraestructure.exception;

public class CategoriesLimitException extends RuntimeException{
    public CategoriesLimitException(String message) {
        super(message);
    }
}
