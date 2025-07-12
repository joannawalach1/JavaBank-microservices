package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequestDto {
    private BigDecimal amount;
}
