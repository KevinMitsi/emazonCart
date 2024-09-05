package com.kevin.emazon_cart.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExceptionResponseDto {
    private String exception;
    private String message;
    private HttpStatus status;
}
