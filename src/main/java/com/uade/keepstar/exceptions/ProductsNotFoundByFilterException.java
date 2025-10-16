package com.uade.keepstar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductsNotFoundByFilterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductsNotFoundByFilterException() {
        super("No hay productos que coincidan con los filtros aplicados.");
    }

    public ProductsNotFoundByFilterException(String message) {
        super(message);
    }
}
