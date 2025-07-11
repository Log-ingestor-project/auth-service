package com.sparky6od.logingestor.auth_service.advice;

import com.sparky6od.logingestor.auth_service.dto.ErrorResponse;
import com.sparky6od.logingestor.auth_service.exception.ClientNotRegisteredException;
import com.sparky6od.logingestor.auth_service.exception.ClientRegistrationException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage() + ". You do not have the necessary permissions to access this resource."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
