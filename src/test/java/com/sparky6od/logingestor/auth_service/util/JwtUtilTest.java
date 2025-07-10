package com.sparky6od.logingestor.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String SECRET = "thisIsATestSecretKeyThatIsLongEnoughForHS256Algorithm";
    private static final long TEST_EXPIRATION = 3600000; // 1 hour in miliseconds

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", TEST_EXPIRATION);
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        // Given
        String clientId = "test-client";
        String serviceName = "test-service";

        // When
        String token = jwtUtil.generateToken(clientId, serviceName);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());

    }

    @Test
    void validateToken_ShouldReturnValidClaims() {
        // Given
        String clientId = "test-client";
        String serviceName = "test-service";
        String token = jwtUtil.generateToken(clientId, serviceName);

        // When
        Claims claims = jwtUtil.validateToken(token);

        // Then
        assertNotNull(claims);
        assertEquals(clientId, claims.getSubject());
        assertEquals(serviceName, claims.get("service"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void validateToken_ShouldThrowException_WhenTokenIsInvalid() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        // TODO: Agregar un Controller Advice para manejar esta exception
        assertThrows(JwtException.class, () -> jwtUtil.validateToken(invalidToken));
    }

    @Test
    void validateToken_ShouldThrowException_WhenTokenIsExpired() {
        // Given
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 1); // Set expiration to 1ms
        String token = jwtUtil.generateToken("test-client", "test-service");

        // When & Then
        try {
            Thread.sleep(10); // Wait for token to expire
            // TODO: Agregar un Controller Advice para manejar esta exception
            assertThrows(JwtException.class, () -> jwtUtil.validateToken(token));
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
    }



}
