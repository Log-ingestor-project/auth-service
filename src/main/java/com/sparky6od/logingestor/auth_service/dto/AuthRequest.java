package com.sparky6od.logingestor.auth_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {

    @NotEmpty(message = "Client ID is required")
    private String clientId;
    @NotEmpty(message = "Client secret is required")
    private String clientSecret;
    @NotEmpty(message = "Service name is required")
    private String serviceName;
    @NotEmpty(message = "The client must have at least one role eg. USER")
    private String roles;

    public AuthRequest() {
    }

    public AuthRequest(String clientId, String clientSecret, String serviceName, String roles) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.serviceName = serviceName;
        this.roles = roles;
    }
}
