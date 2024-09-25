package com.kevin.emazon_cart.domain.usecase;

import com.kevin.emazon_cart.domain.model.Cart;
import com.kevin.emazon_cart.domain.model.ItemCartRequest;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import com.kevin.emazon_cart.domain.model.SaleRequest;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.spi.ISecurityContextPort;
import com.kevin.emazon_cart.domain.spi.external.IConnectionStockPort;
import com.kevin.emazon_cart.domain.spi.external.IConnectionTransactionPort;
import com.kevin.emazon_cart.domain.usecase.helper.CartUseCaseHelper;
import com.kevin.emazon_cart.infraestructure.exception.CategoriesLimitException;
import com.kevin.emazon_cart.infraestructure.exception.EmptyCartException;
import com.kevin.emazon_cart.infraestructure.exception.ItemNotFoundException;
import com.kevin.emazon_cart.infraestructure.exception.NotEnoughItemInStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartUseCaseTest {

    public static final String STOCK_ERROR_MESSAGE = "Stock error";
    private CartUseCase cartUseCase;
    private ICartPersistentPort cartPersistentPort;
    private IConnectionStockPort connectionStockPort;
    private IConnectionTransactionPort connectionTransactionPort;
    private ISecurityContextPort securityContextPort;

    @BeforeEach
    void setUp() {
        cartPersistentPort = Mockito.mock(ICartPersistentPort.class);
        connectionStockPort = Mockito.mock(IConnectionStockPort.class);
        connectionTransactionPort = Mockito.mock(IConnectionTransactionPort.class);
        securityContextPort = Mockito.mock(ISecurityContextPort.class);
        cartUseCase = new CartUseCase(cartPersistentPort, connectionStockPort, connectionTransactionPort, securityContextPort);
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
            assertEquals(CartUseCaseHelper.ADDED_TO_CART_MESSAGE + cart.getItemId(), result);
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

        when(cartPersistentPort.getItemsInUserCart(userId)).thenReturn(List.of(itemId));
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

        when(securityContextPort.userId()).thenReturn(userId);
        when(cartPersistentPort.getItemsInUserCart(userId)).thenReturn(itemIds);
        when(connectionStockPort.findAllProductsInCart(any(ItemCartRequest.class)))
                .thenReturn(expectedResponse);

        // Act
        List<ItemCartResponse> result = cartUseCase.findAllProductsInCart(null, brand);

        // Assert
        assertEquals(expectedResponse, result);
        verify(cartPersistentPort, times(1)).getItemsInUserCart(userId);
        verify(connectionStockPort, times(1)).findAllProductsInCart(any(ItemCartRequest.class));
    }

    @Test
    void buy_ShouldDoNothing_WhenCartIsEmpty() {
        // Arrange
        Long userId = 1L;
        when(securityContextPort.userId()).thenReturn(userId);
        when(cartPersistentPort.findAllCartRecords(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EmptyCartException.class, () -> cartUseCase.buy());

        verify(connectionTransactionPort, never()).createSaleRequest(any(SaleRequest.class));
        verify(connectionStockPort, never()).decreaseQuantityInStock(anyLong(), anyLong());
        verify(cartPersistentPort, never()).deleteAll(anyList(), anyLong());
    }

    @Test
    void buy_ShouldProcessCartSuccessfully_WhenCartIsNotEmpty() {
        // Arrange
        Long userId = 1L;
        List<Cart> productsInCart = Arrays.asList(
                new Cart(null,1L, userId, 2L, null,null), // Producto 1, cantidad 2
                new Cart(null,2L, userId, 1L, null,null)  // Producto 2, cantidad 1
        );

        when(securityContextPort.userId()).thenReturn(userId);
        when(cartPersistentPort.findAllCartRecords(userId)).thenReturn(productsInCart);

        // Act
        cartUseCase.buy();

        // Assert
        verify(connectionTransactionPort, times(1)).createSaleRequest(any(SaleRequest.class));
        verify(connectionStockPort, times(1)).decreaseQuantityInStock(1L, 2L);
        verify(connectionStockPort, times(1)).decreaseQuantityInStock(2L, 1L);
        verify(cartPersistentPort, times(1)).deleteAll(Arrays.asList(1L, 2L), userId);
    }

    @Test
    void buy_ShouldThrowException_WhenErrorOccursWhileDecreasingStock() {
        // Arrange
        Long userId = 1L;
        List<Cart> productsInCart = Arrays.asList(
                new Cart(null,1L, userId, 2L,null,null), // Producto 1, cantidad 2
                new Cart(null,2L, userId, 1L,null,null)  // Producto 2, cantidad 1
        );

        when(securityContextPort.userId()).thenReturn(userId);
        when(cartPersistentPort.findAllCartRecords(userId)).thenReturn(productsInCart);

        doThrow(new RuntimeException(STOCK_ERROR_MESSAGE)).when(connectionStockPort).decreaseQuantityInStock(1L, 2L);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartUseCase.buy());
        assertEquals(STOCK_ERROR_MESSAGE, exception.getMessage());

        verify(cartPersistentPort, never()).deleteAll(anyList(), anyLong());
    }


}
