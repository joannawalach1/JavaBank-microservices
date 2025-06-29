package com.banking.accountservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateDto {
    private String accountNumber;
    private Long userId;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String status = "Active";
}