package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionStockPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionTransactionPort;
import com.kevin.emazon_cart.infraestructure.exception.CategoriesLimitException;
import com.kevin.emazon_cart.infraestructure.exception.ItemIsNotInCartException;
import com.kevin.emazon_cart.infraestructure.exception.ItemNotFoundException;
import com.kevin.emazon_cart.infraestructure.exception.NotEnoughItemInStockResponse;

import java.util.List;

import static com.kevin.emazon_cart.domain.usecase.helper.CartUseCaseHelper.*;

public class CartUseCase implements ICartServicePort {
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
        List<Long> itemsInCart = itemIdsInUserCart(cart.getUserId());

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
    public List<Long> getItemsIdsInUserCart(Long userId) {
        return cartPersistentPort.getItemsInUserCart(userId);
    }
    @Override
    public void deleteByItemId(Long itemId, Long userId) {
        if (!getItemsIdsInUserCart(userId).contains(itemId)){
            throw new ItemIsNotInCartException(ITEM_NOT_IN_USER_CART_EXCEPTION_MESSAGE);
        }

        cartPersistentPort.deleteByItemId(itemId, userId);
        List<Cart> allProductsInCartUpdated = getAllAndUpdateDate(userId);
        cartPersistentPort.saveAll(allProductsInCartUpdated);
    }
    @Override
    public List<ItemCartResponse> findAllProductsInCart(Long userId, Long category, Long brand) {
        List<Long> userItemIds = getItemsIdsInUserCart(userId);
        return checkIfItemsHaveStock(connectionStockPort.findAllProductsInCart(new ItemCartRequest(userItemIds, category, brand)));
    }


    //Methods of the class

    private List<ItemCartResponse> checkIfItemsHaveStock(List<ItemCartResponse> allProductsInCart) {
        allProductsInCart.forEach(item -> {
            String itemStringQuantity = item.getQuantity();
            if (itemStringQuantity.equals(ZERO_STRING_VALUE)) {
                item.setQuantity(NO_STOCK_ITEM +connectionTransactionPort.getItemSupplyDate(item.getItemId()));
            }
        });
        return allProductsInCart;
    }

    private List<Cart> getAllAndUpdateDate(Long userId) {
        List<Cart> allProductsInCart = cartPersistentPort.findAllCartRecords(userId);
        allProductsInCart.forEach(this::prepareDate);
        return allProductsInCart;
    }
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
    private List<Long> itemIdsInUserCart(Long userId) {
        return cartPersistentPort.getItemsInUserCart(userId);
    }
    private String createConfirmationMessage(Cart cart) {
        if (!connectionStockPort.isEnoughStock(cart.getItemId(), cart.getQuantity())){
            throw new NotEnoughItemInStockResponse(NOT_ENOUGH_STOCK_MESSAGE + connectionTransactionPort.getItemSupplyDate(cart.getItemId()));
        }
        return ADDED_TO_CART_MESSAGE + cart.getItemId();
    }
    private void prepareDate(Cart cart) {
        cartPersistentPort.findDateByItemId(cart.getItemId()).ifPresentOrElse(
                existingDate ->{},

                () -> cart.setCreationDate(ACTUAL_DATE)
        );
        cart.setUpdateDate(ACTUAL_DATE);
    }
}
