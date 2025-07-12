package com.banking.accountservice.dto;

import com.banking.accountservice.AccountStatus;
import com.banking.accountservice.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private String accountNumber;
    private String userId;
    @Schema(description = "Typ konta", example = "LOKATA")
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    @Schema(description = "Status konta", example = "ACTIVE")
    private AccountStatus status;
}