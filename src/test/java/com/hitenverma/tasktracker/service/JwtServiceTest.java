package com.hitenverma.tasktracker.service;

import com.hitenverma.tasktracker.model.Role;
import com.hitenverma.tasktracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private User testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L);

        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("hashedpassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals("testuser", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, testUser));
    }

    @Test
    void shouldExtractRoleFromToken() {
        String token = jwtService.generateToken(testUser);
        Object roleClaim = jwtService.extractAllClaims(token).get("role");

        assertEquals("USER", roleClaim);
    }

    @Test
    void shouldIdentifyInvalidUsernameInToken() {
        String token = jwtService.generateToken(testUser);

        User otherUser = User.builder()
                .username("otheruser")
                .password("password")
                .role(Role.USER)
                .build();

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }
}
