package com.springbootrestdemo.users.exceptions;

import com.springbootrestdemo.global.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "User not found";

    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }

    public UserNotFoundException() {
        super(MESSAGE);
    }
}