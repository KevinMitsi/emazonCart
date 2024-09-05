package com.kevin.emazon_cart.infraestructure.controller.advice;

import com.kevin.emazon_cart.application.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerInterceptor {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingIllegalArgument(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto("IllegalArgument Exception", e.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
