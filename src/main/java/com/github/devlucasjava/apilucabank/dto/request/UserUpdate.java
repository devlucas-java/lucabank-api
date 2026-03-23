package com.github.devlucasjava.apilucabank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(
        name = "UserUpdateRequest",
        description = "DTO for partial user update. All fields are optional."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {

    @Schema(description = "User's first name", example = "Lucas", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String firstName;

    @Schema(description = "User's last name", example = "Macedo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String lastName;

    @Schema(description = "User's email address", example = "lucas@lucas.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Schema(description = "User's birth date", example = "2004-08-14", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate birthDate;

    @Schema(description = "User's passport number", example = "AB123456", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String passport;
}