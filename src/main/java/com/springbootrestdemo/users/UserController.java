package com.springbootrestdemo.users;


import com.springbootrestdemo.auth.annotations.AdminOnlyAccess;
import com.springbootrestdemo.auth.annotations.CurrentUser;
import com.springbootrestdemo.global.shared.ApiResponse;
import com.springbootrestdemo.users.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @AdminOnlyAccess
    public ApiResponse<List<UserDto>> findAll(@CurrentUser User currentUser) {
        var users = this.userService.findAll(currentUser);
        var res = StreamSupport.stream(users.spliterator(), false)
                .map(this::toUserDto)
                .toList();
        return ApiResponse.success(res);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> findById(
            @PathVariable Long id,
            @CurrentUser User user
    ) {
        return ApiResponse.success(this.toUserDto(this.userService.findById(id, user)));
    }

    @PostMapping
    public ApiResponse<UserDto> create(@Valid @RequestBody User currentUser) {
        return ApiResponse.success(
                this.toUserDto(
                        this.userService.create(currentUser)
                )
        );
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserDto> update(
            @PathVariable Long id,
            @Valid @RequestBody User changes,
            @CurrentUser User currentUser
    ) {
        return ApiResponse.success(
                this.toUserDto(
                        this.userService.update(id, changes, currentUser)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(
            @PathVariable Long id,
            @CurrentUser User currentUser
    ) {
        this.userService.remove(id, currentUser);
        return ApiResponse.emptySuccess();
    }

    private UserDto toUserDto(User user) {
        return UserDto.of(user);
    }
}
