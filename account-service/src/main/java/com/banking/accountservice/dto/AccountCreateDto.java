package com.banking.accountservice.dto;

import com.banking.accountservice.AccountStatus;
import com.banking.accountservice.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {
    private String id;
    @NotBlank(message = "Account number is required")
    @Size(min = 10, max = 34, message = "Account number must be between 10 and 34 characters")
    private String accountNumber;
    @NotBlank(message = "UserId is required")
    private String userId;
    @Schema(description = "Typ konta", example = "LOKATA")
    private AccountType accountType;
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive or zero")
    private BigDecimal balance;
    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code (e.g., PLN, USD)")
    private String currency;
    @NotNull(message = "Status is required")
    @Schema(description = "Status konta", example = "ACTIVE")
    private AccountStatus status;

}