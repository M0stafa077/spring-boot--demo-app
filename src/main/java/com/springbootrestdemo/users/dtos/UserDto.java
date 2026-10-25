package com.springbootrestdemo.users.dtos;

import com.springbootrestdemo.users.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    Long id;
    String name;
    String email;

    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
