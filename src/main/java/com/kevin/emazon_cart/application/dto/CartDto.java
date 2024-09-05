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
    private static final String NULL_USER_ID_MESSAGE = "El campo userId no puede ser null";
    private static final String NULL_QUANTITY_MESSAGE = "El campo quantity no puede ser null";
    private static final String NEGATIVE_OR_ZERO_QUANTITY_MESSAGE = "El valor del campo quantity debe ser positivo";
    private static final String INVALID_UPDATE_DATE_MESSAGE = "La fecha de actualización (updateDate) no puede ser mayor a la fecha actual";
    private static final String NULL_UPDATE_DATE_MESSAGE = "El campo updateDate no puede ser null";
    private static final String INVALID_CRATED_DATE_MESSAGE = "La fecha de creación (creationDate) no puede ser mayor a la fecha actual";
    private static final String NULL_CREATED_DATE_MESSAGE = "El campo creationDate no puede ser null";

    @NotNull(message = NULL_ITEM_MESSAGE)
    private Long itemId;

    @NotNull(message = NULL_USER_ID_MESSAGE)
    private Long userId;

    @NotNull(message = NULL_QUANTITY_MESSAGE)
    @Positive(message = NEGATIVE_OR_ZERO_QUANTITY_MESSAGE)
    private Double quantity;

    @PastOrPresent(message = INVALID_UPDATE_DATE_MESSAGE)
    @NotNull(message = NULL_UPDATE_DATE_MESSAGE)
    private Date updateDate;

    @PastOrPresent(message = INVALID_CRATED_DATE_MESSAGE)
    @NotNull(message = NULL_CREATED_DATE_MESSAGE)
    private Date creationDate;

}
