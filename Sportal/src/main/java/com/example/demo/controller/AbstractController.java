package com.example.demo.controller;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;


@ControllerAdvice
public class AbstractController {


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(BadRequestException e) {
        return new ErrorDTO(new Date(), "Bad request error", e.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundException(NotFoundException e) {
        return new ErrorDTO(new Date(), "Not found error", e.getMessage());

    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleNotAuthorizedException(AuthenticationException e) {
        return new ErrorDTO(new Date(), "Validation error", e.getMessage());

    }

  /*  @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO customValidationErrorHandling(MethodArgumentNotValidException e) {

     return new ErrorDTO(new Date(), "Validation error",
        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }*/


}
