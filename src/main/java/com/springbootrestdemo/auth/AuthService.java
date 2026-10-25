package com.springbootrestdemo.auth;

import com.springbootrestdemo.auth.dtos.LoginRequestDto;
import com.springbootrestdemo.auth.dtos.LoginResponseDto;
import com.springbootrestdemo.auth.dtos.RegisterRequestDto;
import com.springbootrestdemo.auth.dtos.RegisterResponseDto;
import com.springbootrestdemo.users.User;
import com.springbootrestdemo.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtservice;
    private final AuthenticationManager authManager;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerDetails) {
        User user = new User(
                registerDetails.name(),
                registerDetails.email(),
                registerDetails.password()
        );

        user = this.userService.create(user);
        var accessToken = jwtservice.generateToken(user.getEmail());

        return RegisterResponseDto.of(user, accessToken);
    }

    public LoginResponseDto login(LoginRequestDto loginDetails) {
        this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.email(),
                        loginDetails.password()
                )
        );

        var user = this.userService.findByEmail(loginDetails.email());
        var accessToken = jwtservice.generateToken(user.getEmail());

        return LoginResponseDto.of(user, accessToken);
    }
}
