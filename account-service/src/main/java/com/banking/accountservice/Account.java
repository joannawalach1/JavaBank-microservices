package com.banking.accountservice;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String accountNumber;
    private String userId;
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.SAVINGS;
    private BigDecimal balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
