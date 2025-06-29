package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public static TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAccountId(),
                transaction.getAccountFrom(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getTransactionType(),
                transaction.getTransactionStatus(),
                transaction.getCreatedAt()
        );
    }

    public static Transaction toTransactionEntity(TransactionCreateRequest dto) {
        return new Transaction(
                null,
                dto.getUserId(),
                dto.getAccountId(),
                dto.getAccountFrom(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getTransactionType(),
                "PENDING",
                java.time.LocalDateTime.now()
                );
    }

    public static Transaction toTransactionEntity(TransactionResponseDto dto) {
        return new Transaction(
                dto.getId(),
                dto.getUserId(),
                dto.getAccountId(),
                dto.getAccountFrom(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getTransactionType(),
                dto.getTransactionStatus(),
                dto.getCreatedAt()
        );
    }
}

