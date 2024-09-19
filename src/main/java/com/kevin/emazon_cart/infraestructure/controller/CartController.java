package com.kevin.emazon_cart.infraestructure.controller;

import com.kevin.emazon_cart.application.dto.CartDto;
import com.kevin.emazon_cart.application.handler.ICartHandler;
import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@Validated
public class CartController {
    public static final String ROLE_CLIENT = "ROLE_CLIENTE";
    private static final String DELETED_ITEM_MESSAGE = "Felicidades ha eliminado correctamente de su carrito el item: ";
    public static final String ORDER_DEFAULT_VALUE = "asc";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "6";

    private final ICartHandler cartHandler;

    @PostMapping("/add")
    @Secured(ROLE_CLIENT)
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartDto cartDto){
        cartDto.setUserId((Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return ResponseEntity.status(201).body(cartHandler.addItemToCart(cartDto));
    }

    @DeleteMapping("delete/byItemId/{itemId}")
    @Secured(ROLE_CLIENT)
    public ResponseEntity<String> deleteItemFromCart(@PathVariable Long itemId){
        cartHandler.deleteByItemId(itemId, (Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return ResponseEntity.status(200).body(DELETED_ITEM_MESSAGE+itemId);
    }

    @GetMapping("/myItems")
    @Secured(ROLE_CLIENT)
    public ResponseEntity<Page<ItemCartResponse>>
    getMyItems(@RequestParam(required = false)Long categoryId,
               @RequestParam(required = false)Long brandId,
               @RequestParam(required = false, defaultValue = ORDER_DEFAULT_VALUE)String orderingMethod,
               @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER)Integer pageNumber,
               @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE)Integer pageSize){
        return ResponseEntity.status(HttpStatus.OK).body(
                cartHandler.findAllProductsInCart((Long) SecurityContextHolder.getContext().getAuthentication().getDetails(),
                        categoryId,brandId,orderingMethod,pageNumber,pageSize));
    }


}
