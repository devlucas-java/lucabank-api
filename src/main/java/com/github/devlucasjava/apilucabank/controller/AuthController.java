package com.github.devlucasjava.apilucabank.controller;

import com.github.devlucasjava.apilucabank.dto.request.LoginRequest;
import com.github.devlucasjava.apilucabank.dto.request.RegisterRequest;
import com.github.devlucasjava.apilucabank.dto.response.AuthResponse;
import com.github.devlucasjava.apilucabank.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class)
            )
    )
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        log.debug("Login attempt for user: {}", loginRequest.getLogin());

        AuthResponse authResponse = authService.authenticate(loginRequest);

        log.debug("Login successful for user: {}", loginRequest.getLogin());

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @Operation(
            summary = "User registration",
            description = "Registers a new user and returns a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Registration successful",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        log.debug("Register attempt for user: {}", registerRequest.getEmail());

        AuthResponse authResponse = authService.register(registerRequest);

        log.debug("Registration successful for user: {}", registerRequest.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}