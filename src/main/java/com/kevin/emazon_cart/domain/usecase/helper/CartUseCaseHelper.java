package com.kevin.emazon_cart.domain.usecase.helper;


import java.time.Instant;
import java.util.Date;

public class CartUseCaseHelper {
    public static final Date ACTUAL_DATE = Date.from(Instant.now());
    public static final String ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Este item no existe en la base de datos de Stock";
    public static final String NOT_ENOUGH_STOCK_MESSAGE = "No hay suficientes artículos en Stock para agregar, el ultimo suministro fue el: ";
    public static final String ADDED_TO_CART_MESSAGE = "Ha añadido a su carrito el item: ";
    public static final String CATEGORIES_LIMIT_EXCEED_MESSAGE = "No puede agregar más de 3 artículos con la misma categoría.";
    public static final String ITEM_NOT_IN_USER_CART_EXCEPTION_MESSAGE = "Este item no se encuentra en su carrito";
    public static final String ZERO_STRING_VALUE = "0";
    public static final String NO_STOCK_ITEM = "Item sin stock, supply date: ";

    private CartUseCaseHelper(){}


}
