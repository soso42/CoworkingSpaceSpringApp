package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] ADMIN_ENDPOINTS = {
            "/workspaces",
            "/workspaces/**",
    };
    private static final String[] USER_ENDPOINTS = {
            "/bookings",
            "/bookings/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ADMIN")

                        .requestMatchers(USER_ENDPOINTS).hasAnyAuthority("ADMIN", "USER")

                        .anyRequest().authenticated()

                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
