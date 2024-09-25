package com.kevin.emazon_cart.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaleItemDetailsDto {

    @NotNull
    private Long itemId;

    @NotNull
    @Positive
    private Long quantity;

}
