package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    private String id;
    private Long userId;
    private Long accountId;
    private Long accountFrom;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String transactionStatus;
    private LocalDateTime createdAt;
}
