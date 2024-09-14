package com.kevin.emazon_cart.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
 class CartDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCartDto() {
        CartDto cartDto = new CartDto(1L, 2L, 3L, new Date(), new Date());
        Set<ConstraintViolation<CartDto>> violations = validator.validate(cartDto);
        assertTrue(violations.isEmpty(), "Valid CartDto should have no violations");
    }

    @Test
    void testNullItemId() {
        CartDto cartDto = new CartDto(null, 2L, 3L, new Date(), new Date());
        Set<ConstraintViolation<CartDto>> violations = validator.validate(cartDto);
        assertFalse(violations.isEmpty());
        assertEquals("el item no puede ser null", violations.iterator().next().getMessage());
    }

    @Test
    void testNullQuantity() {
        CartDto cartDto = new CartDto(1L, 2L, null, new Date(), new Date());
        Set<ConstraintViolation<CartDto>> violations = validator.validate(cartDto);
        assertFalse(violations.isEmpty());
        assertEquals("El campo quantity no puede ser null", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeOrZeroQuantity() {
        CartDto cartDto = new CartDto(1L, 2L, -1L, new Date(), new Date());
        Set<ConstraintViolation<CartDto>> violations = validator.validate(cartDto);
        assertFalse(violations.isEmpty());
        assertEquals("El valor del campo quantity debe ser positivo", violations.iterator().next().getMessage());
    }

}
