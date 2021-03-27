package io.tacsio.mercadolivre.config;

import io.tacsio.mercadolivre.service.order.ProductUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getConstraintViolations());
    }

    @ExceptionHandler({ProductUnavailableException.class})
    public ResponseEntity handleProductUnavailableException(ProductUnavailableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String name;
            if (error instanceof FieldError) {
                var fieldObject = (FieldError) error;
                name = fieldObject.getField();
            } else {
                name = error.getObjectName().replaceAll("Request", "");
            }

            String errorMsg = String.format("[%s]: %s", name, error.getDefaultMessage());
            errors.add(errorMsg);
        });

        return errors;
    }
}
