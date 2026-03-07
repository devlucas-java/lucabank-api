package com.github.devlucasjava.apilucabank.controller;

import com.github.devlucasjava.apilucabank.dto.response.AccountResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Get current user's account",
            description = "Returns account information of the authenticated user"
    )
    @ApiResponse(responseCode = "200", description = "Account retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
    @ApiResponse(responseCode = "404", description = "Account not found")

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ACCOUNT')")
    public ResponseEntity<AccountResponse> getMyAccount(@AuthenticationPrincipal Users users) {
        AccountResponse response = accountService.getMyAccount(users);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}