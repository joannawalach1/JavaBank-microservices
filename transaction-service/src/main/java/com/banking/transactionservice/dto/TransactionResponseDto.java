package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    private UUID id;
    private Long userId;
    private String accountId;
    private Long accountFrom;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String transactionStatus;
    private LocalDateTime createdAt;
}
