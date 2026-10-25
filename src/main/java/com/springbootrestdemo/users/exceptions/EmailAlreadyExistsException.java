package com.springbootrestdemo.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {
   private static final String MESSAGE = "Email already exists";

    public EmailAlreadyExistsException() {}

   public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
