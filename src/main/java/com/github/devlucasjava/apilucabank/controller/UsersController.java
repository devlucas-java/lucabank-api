package com.github.devlucasjava.apilucabank.controller;

import com.github.devlucasjava.apilucabank.dto.request.UserUpdate;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "Endpoints related to users")
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Get authenticated user", description = "Returns the currently authenticated user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated user returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me")
    public ResponseEntity<UsersResponse> getMe(@AuthenticationPrincipal Users users) {
        log.debug("Fetching authenticated user profile: {}", users.getEmail());
        UsersResponse userResponse = usersService.getAuthenticatedUser(users);
        log.debug("Authenticated user profile returned successfully: {}", users.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(
            summary = "Update user",
            description = "Updates user information such as name, email or password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/me")
    public ResponseEntity<UsersResponse> updateMe(

            @AuthenticationPrincipal
            Users user,

            @RequestBody
            @Parameter(description = "User update payload")
            UserUpdate dto
    ) {
        UsersResponse response = usersService.updateMe(user, dto);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete authenticated user",
            description = "Deletes the currently authenticated user's account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(

            @Parameter(hidden = true)
            @AuthenticationPrincipal Users users
    ) {
        log.warn("Request: DELETE /user/me | user={}", users.getEmail());

        usersService.deleteMe(users);

        log.warn("User deleted successfully | user={}", users.getEmail());

        return ResponseEntity.ok().build();
    }
}