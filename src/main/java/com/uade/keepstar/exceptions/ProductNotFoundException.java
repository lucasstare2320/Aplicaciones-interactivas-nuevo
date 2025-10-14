package com.uade.keepstar.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "producto no encontrado")


public class ProductNotFoundException extends Exception {
    
}
