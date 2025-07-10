package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {
    private Long userId;
    private String accountId;
    private Long accountFrom;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
}
