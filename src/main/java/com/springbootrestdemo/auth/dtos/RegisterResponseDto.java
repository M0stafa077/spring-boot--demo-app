package com.springbootrestdemo.auth.dtos;

import com.springbootrestdemo.users.User;
import com.springbootrestdemo.users.dtos.UserDto;

public record RegisterResponseDto(
        UserDto user,
        String accessToken
) {
    public static RegisterResponseDto of(UserDto user, String accessToken) {
        return new RegisterResponseDto(user, accessToken);
    }

    public static RegisterResponseDto of(User user, String accessToken) {
        return new RegisterResponseDto(UserDto.of(user), accessToken);
    }
}
