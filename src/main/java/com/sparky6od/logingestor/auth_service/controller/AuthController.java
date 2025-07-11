package com.sparky6od.logingestor.auth_service.controller;

import com.sparky6od.logingestor.auth_service.dto.AuthRequest;
import com.sparky6od.logingestor.auth_service.dto.AuthResponse;
import com.sparky6od.logingestor.auth_service.dto.CreationResponse;
import com.sparky6od.logingestor.auth_service.exception.ClientNotRegisteredException;
import com.sparky6od.logingestor.auth_service.exception.ClientRegistrationException;
import com.sparky6od.logingestor.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public AuthResponse getToken(@RequestBody @Valid AuthRequest request) throws ClientNotRegisteredException {
        return authService.authenticate(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public CreationResponse register(@RequestBody @Valid AuthRequest request) throws ClientRegistrationException {
        return authService.register(request);
    }
}
