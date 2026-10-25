package com.springbootrestdemo.auth.dtos;

import com.springbootrestdemo.users.User;
import com.springbootrestdemo.users.dtos.UserDto;

public record LoginResponseDto(
        UserDto user,
        String accessToken
) {

    public static LoginResponseDto of(UserDto user, String accessToken) {
        return new LoginResponseDto(user, accessToken);
    }

    public static LoginResponseDto of(User user, String accessToken) {
        return new LoginResponseDto(UserDto.of(user), accessToken);
    }
}
