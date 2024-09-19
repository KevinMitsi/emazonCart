package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionStockPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionTransactionPort;
import com.kevin.emazon_cart.infraestructure.exception.CategoriesLimitException;
import com.kevin.emazon_cart.infraestructure.exception.ItemNotFoundException;
import com.kevin.emazon_cart.infraestructure.exception.NotEnoughItemInStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartUseCaseTest {

    private CartUseCase cartUseCase;
    private ICartPersistentPort cartPersistentPort;
    private IConnectionStockPort connectionStockPort;
    private IConnectionTransactionPort connectionTransactionPort;

    @BeforeEach
    void setUp() {
        cartPersistentPort = Mockito.mock(ICartPersistentPort.class);
        connectionStockPort = Mockito.mock(IConnectionStockPort.class);
        connectionTransactionPort = Mockito.mock(IConnectionTransactionPort.class);
        cartUseCase = new CartUseCase(cartPersistentPort, connectionStockPort, connectionTransactionPort);
    }

    @Nested
    class AddItemToCartTests {

        @Test
        void addItemToCart_ShouldAddItem_WhenValidationsPass() {
            // Arrange
            Cart cart = new Cart(1L, 1L, 2L, 5L, null, null);

            when(connectionStockPort.existsItemById(cart.getItemId())).thenReturn(true);
            when(connectionStockPort.validateCategoryLimit(anyList())).thenReturn(true);
            when(connectionStockPort.isEnoughStock(cart.getItemId(), cart.getQuantity())).thenReturn(true);

            // Act
            String result = cartUseCase.addItemToCart(cart);

            // Assert
            assertEquals(CartUseCase.ADDED_TO_CART_MESSAGE + cart.getItemId(), result);
            verify(cartPersistentPort, times(1)).addItemToCart(cart);
        }

        @Test
        void addItemToCart_ShouldThrowItemNotFoundException_WhenItemDoesNotExistInStock() {
            // Arrange
            Cart cart = new Cart(1L, 1L, 2L, 5L, null, null);

            when(connectionStockPort.existsItemById(cart.getItemId())).thenReturn(false);

            // Act & Assert
            assertThrows(ItemNotFoundException.class, () -> cartUseCase.addItemToCart(cart));
            verify(cartPersistentPort, never()).addItemToCart(any());
        }

        @Test
        void addItemToCart_ShouldThrowCategoriesLimitException_WhenCategoryLimitIsExceeded() {
            // Arrange
            Cart cart = new Cart(1L, 1L, 2L, 5L, null, null);
            when(connectionStockPort.existsItemById(cart.getItemId())).thenReturn(true);
            when(connectionStockPort.validateCategoryLimit(anyList())).thenReturn(false);

            // Act & Assert
            assertThrows(CategoriesLimitException.class, () -> cartUseCase.addItemToCart(cart));
            verify(cartPersistentPort, never()).addItemToCart(any());
        }

        @Test
        void addItemToCart_ShouldThrowNotEnoughItemInStockResponse_WhenStockIsNotEnough() {
            // Arrange
            Cart cart = new Cart(1L, 1L, 2L, 5L, null, null);

            when(connectionStockPort.existsItemById(cart.getItemId())).thenReturn(true);
            when(connectionStockPort.validateCategoryLimit(anyList())).thenReturn(true);
            when(connectionStockPort.isEnoughStock(cart.getItemId(), cart.getQuantity())).thenReturn(false);
            when(connectionTransactionPort.getItemSupplyDate(cart.getItemId())).thenReturn(Date.from(Instant.now()));

            // Act & Assert
            assertThrows(NotEnoughItemInStockResponse.class, () -> cartUseCase.addItemToCart(cart));
            verify(cartPersistentPort, never()).addItemToCart(any());
        }
    }

    @Test
    void getItemsInUserCart_ShouldReturnItemsInCart() {
        // Arrange
        Long userId = 1L;
        List<Long> items = List.of(2L, 3L);
        when(cartPersistentPort.getItemsInUserCart(userId)).thenReturn(items);

        // Act
        List<Long> result = cartUseCase.getItemsIdsInUserCart(userId);

        // Assert
        assertEquals(items, result);
        verify(cartPersistentPort, times(1)).getItemsInUserCart(userId);
    }

    @Test
    void deleteByItemId_ShouldDeleteItemFromCart() {
        // Arrange
        Long itemId = 2L;
        Long userId = 1L;

        // Act
        cartUseCase.deleteByItemId(itemId, userId);

        // Assert
        verify(cartPersistentPort, times(1)).deleteByItemId(itemId, userId);
    }
    @Test
    void findAllProductsInCart_ShouldReturnItemsInCart() {
        // Arrange
        Long userId = 1L;
        Long brand = 3L;
        List<Long> itemIds = List.of(2L, 3L);
        List<ItemCartResponse> expectedResponse = List.of(
                new ItemCartResponse(2L, "Item 2", brand, "3L"),
                new ItemCartResponse(3L, "Item 3", brand, "3L")
        );

        when(cartPersistentPort.getItemsInUserCart(userId)).thenReturn(itemIds);
        when(connectionStockPort.findAllProductsInCart(any(ItemCartRequest.class)))
                .thenReturn(expectedResponse);

        // Act
        List<ItemCartResponse> result = cartUseCase.findAllProductsInCart(userId,null, brand);

        // Assert
        assertEquals(expectedResponse, result);
        verify(cartPersistentPort, times(1)).getItemsInUserCart(userId);
        verify(connectionStockPort, times(1)).findAllProductsInCart(any(ItemCartRequest.class));
    }
}
