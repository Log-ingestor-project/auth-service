package com.sparky6od.logingestor.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    private String clientId;
    private String clientSecret; // Hasheado con BCrypt
    private String serviceName;
    private String roles; // Separated by commas eg. "ADMIN,USER"
    private boolean active;

}
