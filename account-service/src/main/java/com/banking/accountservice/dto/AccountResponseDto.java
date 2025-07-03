package com.banking.accountservice.dto;

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
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String status = "Active";
}