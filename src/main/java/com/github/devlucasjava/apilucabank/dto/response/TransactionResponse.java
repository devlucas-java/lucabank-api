package com.github.devlucasjava.apilucabank.dto.response;
import com.github.devlucasjava.apilucabank.model.StatusEnum;
import com.github.devlucasjava.apilucabank.model.TypeTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Transaction response")
public class TransactionResponse {

    @Schema(description = "Transaction unique identifier",
            example = "660e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Timestamp when sender made the transaction",
            example = "2024-01-15T14:30:00")
    private LocalDateTime timeStampSender;

    @Schema(description = "Timestamp when receiver received the transaction",
            example = "2024-01-15T14:30:05")
    private LocalDateTime timeStampReceiver;

    @Schema(description = "Transaction description", example = "Payment for dinner")
    private String description;

    @Schema(description = "Admin description (if any)", example = "Approved")
    private String descriptionAdmin;

    @Schema(description = "Transaction amount", example = "150.00")
    private BigDecimal amount;

    @Schema(description = "Transaction status", example = "COMPLETED",
            allowableValues = {"PENDING", "COMPLETED", "CANCELLED", "REJECTED"})
    private StatusEnum status;

    @Schema(description = "Transaction type", example = "TRANSFER",
            allowableValues = {"TRANSFER", "DEPOSIT", "WITHDRAW"})
    private TypeTransaction typeTransaction;

    @Schema(description = "Sender account ID",
            example = "550e8400-e29b-41d4-a716-446655440000")
    private AccountSummaryResponse sender;

    @Schema(description = "Receiver account ID",
            example = "770e8400-e29b-41d4-a716-446655440000")
    private AccountSummaryResponse receiver;
}