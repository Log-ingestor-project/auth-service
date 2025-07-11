package com.sparky6od.logingestor.auth_service.config;

import com.sparky6od.logingestor.auth_service.model.Client;
import com.sparky6od.logingestor.auth_service.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    @Profile("dev")
    CommandLineRunner init(ClientRepository clientRepository) {
        return args -> {
            if (clientRepository.findByClientId("test-client").isEmpty()) {
                Client client = new Client();
                client.setClientId("test-client");
                client.setClientSecret(new BCryptPasswordEncoder().encode("test-client-secret"));
                client.setServiceName("test-service");
                client.setRoles("ADMIN");
                client.setActive(true);
                clientRepository.save(client);
                System.out.println("✅ Client created");
            }
        };
    }

}
