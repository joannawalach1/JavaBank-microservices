package com.banking.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {
    private Long id;
    private String accountNumber;
    private String userId;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String status = "Active";
}