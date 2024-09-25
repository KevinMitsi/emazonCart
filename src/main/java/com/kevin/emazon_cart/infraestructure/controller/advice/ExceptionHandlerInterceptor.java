package com.kevin.emazon_cart.infraestructure.controller.advice;

import com.kevin.emazon_cart.application.dto.ExceptionResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.List;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerInterceptor {

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionResponseDto> inCaseThrowingConnectException(ConnectException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponseDto>> inCaseThrowingConstraintViolationException(MethodArgumentNotValidException e){
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body(e.getFieldErrors().
                stream().
                map(ex -> new ExceptionResponseDto("Error en la creación del campo: "+ex.getField() ,ex.getDefaultMessage(), HttpStatus.BAD_REQUEST)).toList());
    }

}
