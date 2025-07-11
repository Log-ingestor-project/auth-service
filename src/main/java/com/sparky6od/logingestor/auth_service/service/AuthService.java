package com.sparky6od.logingestor.auth_service.service;

import com.sparky6od.logingestor.auth_service.dto.AuthRequest;
import com.sparky6od.logingestor.auth_service.dto.AuthResponse;
import com.sparky6od.logingestor.auth_service.dto.CreationResponse;
import com.sparky6od.logingestor.auth_service.exception.ClientNotRegisteredException;
import com.sparky6od.logingestor.auth_service.exception.ClientRegistrationException;
import com.sparky6od.logingestor.auth_service.model.Client;
import com.sparky6od.logingestor.auth_service.repository.ClientRepository;
import com.sparky6od.logingestor.auth_service.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(JwtUtil jwtUtil, ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(AuthRequest request) throws ClientNotRegisteredException {
        Client client = clientRepository.findByClientId(request.getClientId()).orElseThrow(() -> new ClientNotRegisteredException("Client not registered"));
        // If the client secret does not match to client secret in the database
        if(!passwordEncoder.matches(request.getClientSecret(), client.getClientSecret()))
            throw new ClientNotRegisteredException("Invalid client secret");

        return new AuthResponse(jwtUtil.generateToken(request));
    }

    public CreationResponse register(AuthRequest request) throws ClientRegistrationException {

        if(clientRepository.findByClientId(request.getClientId()).isPresent()) {
            throw new ClientRegistrationException("Client already registered");
        }

        try {
            Client client = new Client();
            client.setClientId(request.getClientId());
            client.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
            client.setServiceName(request.getServiceName());
            client.setRoles(request.getRoles());
            client.setActive(true);
            clientRepository.save(client);
            return new CreationResponse(client.getClientId(), client.getClientSecret(), client.getServiceName(), client.getRoles(), client.isActive());
        } catch (Exception e) {
            throw new ClientRegistrationException("Error registering client");
        }
    }
}
