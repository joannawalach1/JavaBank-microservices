package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequestDto {
    private BigDecimal amount;
    private String transactionType;
    private String AccountId;
}
