package com.springbootrestdemo.auth.providers;

import com.springbootrestdemo.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthProvider {

    @Bean
    public AuthenticationManager authManager(
            UserService userDetailsService,
            PasswordEncoder pwdEncoder
    ) {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(pwdEncoder);
        return new ProviderManager(provider);
    }
}
