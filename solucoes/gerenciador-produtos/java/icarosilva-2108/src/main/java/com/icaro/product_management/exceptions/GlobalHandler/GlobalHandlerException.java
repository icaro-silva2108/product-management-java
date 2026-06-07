package com.icaro.product_management.exceptions.GlobalHandler;

import com.icaro.product_management.exceptions.CategoryAlreadyExistsException;
import com.icaro.product_management.exceptions.CategoryHasProductsException;
import com.icaro.product_management.exceptions.ProductAlreadyExistsException;
import com.icaro.product_management.exceptions.ResourceNotFoundException;
import com.icaro.product_management.exceptions.dtos.GenericResponseExceptionDTO;

import com.icaro.product_management.exceptions.dtos.ValidationErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<GenericResponseExceptionDTO> resourceNotFoundHandler(ResourceNotFoundException exception) {

        GenericResponseExceptionDTO errorResponse = new GenericResponseExceptionDTO(404, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    private ResponseEntity<GenericResponseExceptionDTO> productAlreadyExistsHandler(ProductAlreadyExistsException exception) {

        GenericResponseExceptionDTO errorResponse = new GenericResponseExceptionDTO(409, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    private ResponseEntity<GenericResponseExceptionDTO> categoryAlreadyExistsHandler(CategoryAlreadyExistsException exception) {

        GenericResponseExceptionDTO errorResponse = new GenericResponseExceptionDTO(409, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CategoryHasProductsException.class)
    private ResponseEntity<GenericResponseExceptionDTO> categoryHasProductsException(CategoryHasProductsException exception) {

        GenericResponseExceptionDTO errorResponse = new GenericResponseExceptionDTO(409, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ValidationErrorResponseDTO> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ValidationErrorResponseDTO errorResponse = new ValidationErrorResponseDTO(400, "field validation errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<GenericResponseExceptionDTO> InvalidJsonHandler(HttpMessageNotReadableException exception) {

        GenericResponseExceptionDTO errorResponse = new GenericResponseExceptionDTO(400, "invalid JSON body request");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}