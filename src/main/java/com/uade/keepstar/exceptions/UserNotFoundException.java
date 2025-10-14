package com.uade.keepstar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus
(code = HttpStatus.NOT_FOUND, reason = "usuario no encontrado")
public class UserNotFoundException extends Exception {



}
