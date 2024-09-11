package com.kevin.emazon_cart.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {

    private static final String NULL_ITEM_MESSAGE = "el item no puede ser null";
    private static final String NULL_QUANTITY_MESSAGE = "El campo quantity no puede ser null";
    private static final String NEGATIVE_OR_ZERO_QUANTITY_MESSAGE = "El valor del campo quantity debe ser positivo";

    @NotNull(message = NULL_ITEM_MESSAGE)
    private Long itemId;

    private Long userId;

    @NotNull(message = NULL_QUANTITY_MESSAGE)
    @Positive(message = NEGATIVE_OR_ZERO_QUANTITY_MESSAGE)
    private Double quantity;


    private Date updateDate;

    private Date creationDate;

}
