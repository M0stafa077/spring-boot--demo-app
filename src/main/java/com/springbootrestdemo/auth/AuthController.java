package com.springbootrestdemo.auth;

import com.springbootrestdemo.auth.dtos.LoginRequestDto;
import com.springbootrestdemo.auth.dtos.LoginResponseDto;
import com.springbootrestdemo.auth.dtos.RegisterRequestDto;
import com.springbootrestdemo.auth.dtos.RegisterResponseDto;
import com.springbootrestdemo.global.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto registerDetails) {
        return ApiResponse.success(this.authService.register(registerDetails));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
        return ApiResponse.success(this.authService.login(loginDto));
    }
}
