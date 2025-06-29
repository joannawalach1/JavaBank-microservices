package com.banking.transactionservice;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "transactions")
public class Transaction {
    @PrimaryKey
    private String id;
    private Long userId;
    private Long accountId;
    private Long accountFrom;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private String transactionType;
    @Enumerated(EnumType.STRING)
    private String transactionStatus;
    private LocalDateTime createdAt;

    public Transaction(String string, Long userId, Long accountTo, Long accountFrom, BigDecimal amount, String currency, String currency1, String transactionType) {
    }
}
