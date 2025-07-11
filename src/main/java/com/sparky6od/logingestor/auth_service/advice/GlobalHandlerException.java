package com.sparky6od.logingestor.auth_service.advice;

import com.sparky6od.logingestor.auth_service.dto.ErrorResponse;
import com.sparky6od.logingestor.auth_service.exception.ClientNotRegisteredException;
import com.sparky6od.logingestor.auth_service.exception.ClientRegistrationException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ClientNotRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailure(ClientNotRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid/Expired token"));
    }

    @ExceptionHandler(ClientRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleClientRegistrationException(ClientRegistrationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

}
