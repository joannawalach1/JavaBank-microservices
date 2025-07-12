package com.banking.accountservice;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Typ konta", example = "LOKATA")
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Status konta", example = "ACTIVE")
    private AccountStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
