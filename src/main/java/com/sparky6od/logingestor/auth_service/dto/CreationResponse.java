package com.sparky6od.logingestor.auth_service.dto;

public record CreationResponse(
        String clientId,
        String clientSecret,
        String serviceName,
        String roles,
        boolean active
) {
}
