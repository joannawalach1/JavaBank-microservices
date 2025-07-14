package com.banking.transactionservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")

public class Transaction {
    @PrimaryKey
    private UUID id;

    @Column("user_id")
    private Long userId;

    @Column("account_id")
    private String accountId;

    @Column("account_from")
    private Long accountFrom;

    @Column("amount")
    private BigDecimal amount;

    @Column("currency")
    private String currency;

    @Column("transaction_type")
    private String transactionType;

    @Column("transaction_status")
    private String transactionStatus;

    @Column("created_at")
    private LocalDateTime createdAt;

    public Transaction(String string, Long userId, Long accountTo, Long accountFrom, BigDecimal amount, String currency, String currency1, String transactionType) {
    }
}
