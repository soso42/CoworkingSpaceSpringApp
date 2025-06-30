package org.example.service.impl;

import org.example.enums.AccessLevel;
import org.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AuthServiceImplTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        this.authService = new AuthServiceImpl();
    }


    @Test
    void setAccessLevel() {
        // Given
        AccessLevel accessLevel = AccessLevel.ADMIN;

        // When
        authService.setAccessLevel(accessLevel);

        // Then
        assertEquals(accessLevel, authService.getAccessLevel());
    }

}
