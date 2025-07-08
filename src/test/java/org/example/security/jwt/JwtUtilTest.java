package org.example.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Test
    void generateAdminJWT() {
        String jwt = JwtUtil.generateToken("soso", "ADMIN");
        System.out.println("-------------------");
        System.out.println(jwt);
        System.out.println("-------------------");
    }

    @Test
    void generateUserJWT() {
        String jwt = JwtUtil.generateToken("jemo", "USER");
        System.out.println("-------------------");
        System.out.println(jwt);
        System.out.println("-------------------");
    }

}
