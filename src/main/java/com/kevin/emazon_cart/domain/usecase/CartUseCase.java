package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionStockPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionTransactionPort;
import com.kevin.emazon_cart.infraestructure.exception.CategoriesLimitException;
import com.kevin.emazon_cart.infraestructure.exception.ItemNotFoundException;
import com.kevin.emazon_cart.infraestructure.exception.NotEnoughItemInStockResponse;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class CartUseCase implements ICartServicePort {
    public static final Date ACTUAL_DATE = Date.from(Instant.now());
    public static final String ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Este item no existe en la base de datos de Stock";
    public static final String NOT_ENOUGH_STOCK_MESSAGE = "No hay suficientes artículos en Stock para agregar, el ultimo suministro fue el: ";
    public static final String ADDED_TO_CART_MESSAGE = "Ha añadido a su carrito el item: ";
    public static final String CATEGORIES_LIMIT_EXCEED_MESSAGE = "No puede agregar más de 3 artículos con la misma categoría.";
    private final ICartPersistentPort cartPersistentPort;
    private final IConnectionStockPort connectionStockPort;
    private final IConnectionTransactionPort connectionTransactionPort;


    public CartUseCase(ICartPersistentPort cartPersistentPort, IConnectionStockPort connectionStockPort, IConnectionTransactionPort connectionTransactionPort) {
        this.cartPersistentPort = cartPersistentPort;
        this.connectionStockPort = connectionStockPort;
        this.connectionTransactionPort = connectionTransactionPort;
    }


    @Override
    public String addItemToCart(Cart cart) {
        validateIfItemExistInStock(cart);
        List<Long> itemsInCart = fillItemIdsOfUser(cart);

        if (itemsInCart.contains(cart.getItemId())){
            return updatedItemInStock(cart);
        }

        itemsInCart.add(cart.getItemId());
        validateCategoriesInStockPort(itemsInCart);
        prepareDate(cart);
        String createdMessageResponse = createConfirmationMessage(cart);

        cartPersistentPort.addItemToCart(cart);
        return createdMessageResponse;
    }

    @Override
    public List<Long> getItemsInUserCart(Long userId) {
        return cartPersistentPort.getItemsInUserCart(userId);
    }

    @Override
    public void deleteByItemId(Long itemId) {
        cartPersistentPort.deleteByItemId(itemId);
    }


    //Methods of the class


    private String updatedItemInStock(Cart cart) {
        Long savedQuantity = cartPersistentPort.getItemQuantityByItemId(cart.getItemId());

        prepareDate(cart);
        cart.setQuantity(savedQuantity+cart.getQuantity());

        String messageResult = createConfirmationMessage(cart);
        cartPersistentPort.updateItemQuantity(cart);
        return messageResult;
    }
    private void validateIfItemExistInStock(Cart cart) {
        if (!connectionStockPort.existsItemById(cart.getItemId())){
            throw new ItemNotFoundException(ITEM_NOT_FOUND_EXCEPTION_MESSAGE);
        }
    }
    private void validateCategoriesInStockPort(List<Long> itemsInCart) {
        if (!connectionStockPort.validateCategoryLimit(itemsInCart)){
            throw new CategoriesLimitException(CATEGORIES_LIMIT_EXCEED_MESSAGE);
        }
    }
    private List<Long> fillItemIdsOfUser(Cart cart) {
        return cartPersistentPort.getItemsInUserCart(cart.getUserId());
    }
    private String createConfirmationMessage(Cart cart) {
        if (!connectionStockPort.isEnoughStock(cart.getItemId(), cart.getQuantity())){
            throw new NotEnoughItemInStockResponse(NOT_ENOUGH_STOCK_MESSAGE + connectionTransactionPort.getItemSupplyDate(cart.getItemId()));
        }
        return ADDED_TO_CART_MESSAGE + cart.getItemId();
    }


    private void prepareDate(Cart cart) {
        cartPersistentPort.findDateByItemId(cart.getItemId()).ifPresentOrElse(existingDate ->{},
                () -> cart.setCreationDate(ACTUAL_DATE)
        );
        cart.setUpdateDate(ACTUAL_DATE);
    }
}
