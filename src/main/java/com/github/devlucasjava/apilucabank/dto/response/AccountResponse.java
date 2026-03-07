package com.github.devlucasjava.apilucabank.dto.response;

import com.github.devlucasjava.apilucabank.model.Transactions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Account response")
public class AccountResponse {

    @Schema(description = "Unique identifier of the account", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "List of transactions")
    private List<Transactions> transactionsList;

    @Schema(description = "Balance total", example = "2500")
    private BigDecimal balance;

    @Schema(description = "Maximum amount of money you can transfer in a transaction", example = "1000")
    private BigDecimal maxAmountTransaction;

    @Schema(description = "Account is blocked?", example = "false")
    private boolean isBlocke;

    @Schema(description = "Date blocked", example = "2026-02-27T17:50:18")
    private LocalDateTime blockedDate;

    @Schema(description = "User who owns the account", example = "2026-02-27T17:50:18")
    private UUID userId;
}