package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ADMIN_ENDPOINTS = {
            "/workspaces",
            "/workspaces/**",
    };
    private static final String[] USER_ENDPOINTS = {
            "/bookings",
            "/bookings/**"
    };
    private static final String[] OPEN_ENDPOINTS = {
            "/users/login",
            "/users/signup"
    };

    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ADMIN")

                        .requestMatchers(USER_ENDPOINTS).hasAnyAuthority("ADMIN", "USER")

                        .requestMatchers(OPEN_ENDPOINTS).permitAll()

                        .anyRequest().denyAll()

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
