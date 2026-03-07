package com.github.devlucasjava.apilucabank.dto.response;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Basic account information used in transaction responses")
public record AccountSummaryResponse(

        @Schema(
                description = "Account unique identifier",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Account holder name",
                example = "Lucas Silva"
        )
        String name

) {}