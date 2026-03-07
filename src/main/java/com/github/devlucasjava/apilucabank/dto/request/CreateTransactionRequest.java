package com.github.devlucasjava.apilucabank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request transaction")
public class CreateTransactionRequest {
    @Schema(description = "Receiver account ID",
            example = "770e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Receiver account ID is required")
    private UUID receiverId;

    @Schema(description = "Transaction amount",
            example = "150.00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @Schema(description = "Transaction description",
            example = "Payment for dinner",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @Schema(description = "Transaction type",
            example = "TRANSFER",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"TRANSFER", "DEPOSIT", "WITHDRAW"})
    @NotNull(message = "Transaction type is required")
    private String typeTransaction;
}