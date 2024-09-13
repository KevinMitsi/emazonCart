package com.kevin.emazon_cart.infraestructure.controller.advice;

import com.kevin.emazon_cart.application.dto.ExceptionResponseDto;
import com.kevin.emazon_cart.infraestructure.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerInterceptor {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingIllegalArgument(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(FeignRequestException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingFeignRequestException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(FeignServerException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingFeignServerException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingItemNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(NotEnoughItemInStockResponse.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingNotEnoughItemException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingConnectException(ConnectException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(CategoriesLimitException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseCategoryLimitException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }


}
