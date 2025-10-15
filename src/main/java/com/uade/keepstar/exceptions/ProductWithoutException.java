package com.uade.keepstar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "producto sin stock")


public class ProductWithoutException extends Exception {
    
}
