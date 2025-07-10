package com.sparky6od.logingestor.auth_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Load application context
@AutoConfigureMockMvc // Testing endpoints
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_ShouldBeAccessibleWithoutAuthentication() throws Exception {
        // Test /auth/token endpoint
        mockMvc.perform(post("/auth/token"))
                .andExpect(status().isNotFound()); // 404 because controller doesn't exist yet

        // Test /auth/register endpoint
        mockMvc.perform(post("/auth/register"))
                .andExpect(status().isNotFound()); // 404 because controller doesn't exist yet
    }

    @Test
    void protectedEndpoints_ShouldRequireAuthentication() throws Exception {
        // Test some random protected endpoint
        mockMvc.perform(get("/api/something"))
                .andExpect(status().isForbidden()); // 403 because authentication is required
    }

    @Test
    void csrfProtection_ShouldBeDisabled() throws Exception {
        // POST to public endpoint without CSRF token should work
        mockMvc.perform(post("/auth/token"))
                .andExpect(status().isNotFound()) // 404 because controller doesn't exist yet
                .andReturn();
    }

}
