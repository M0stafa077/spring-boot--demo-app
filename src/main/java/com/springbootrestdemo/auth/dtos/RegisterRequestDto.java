package com.springbootrestdemo.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequestDto(
        @NotEmpty
        String name,

        @Email
        String email,

        @NotEmpty
        String password
) {
}
