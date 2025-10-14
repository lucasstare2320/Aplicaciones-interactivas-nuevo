package com.uade.keepstar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "usuario no autorizado")
public class UnauthorizedException extends Exception {
    
}
