package com.github.devlucasjava.apilucabank.controller;

import com.github.devlucasjava.apilucabank.dto.request.CreateTransactionRequest;
import com.github.devlucasjava.apilucabank.dto.response.TransactionResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Bank transaction management API")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/send")
    @Operation(
            summary = "Create a new transaction",
            description = "Creates a financial transaction for the authenticated user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transaction successfully created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransactionResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PreAuthorize("hasAuthority('SEND_TRANSACTIONS')")
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody CreateTransactionRequest request,
            @AuthenticationPrincipal Users users
    ) {

        TransactionResponse response =
                transactionService.createTransaction(request, users);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}