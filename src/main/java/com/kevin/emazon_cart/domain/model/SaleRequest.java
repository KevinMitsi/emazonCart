package com.kevin.emazon_cart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequest {

    private List<SaleItemDetails> saleItemsDto;

    private Date saleDate;

    private PaymentStatus paymentStatus;

}