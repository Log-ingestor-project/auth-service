package com.sparky6od.logingestor.auth_service.repository;

import com.sparky6od.logingestor.auth_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByClientId(String clientId);

}
