package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import com.datastax.oss.driver.shaded.guava.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponseDto getTransactionsById(String transactionId) {
        Transaction byId = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + transactionId));
        return TransactionMapper.toTransactionResponseDto(byId);
    }

    public Transaction createTransaction(TransactionCreateRequest transactionCreateRequest) {
        BigDecimal amount = transactionCreateRequest.getAmount();
        Long accountFrom = transactionCreateRequest.getAccountFrom();
        Long accountTo = transactionCreateRequest.getAccountId();
        Long userId = transactionCreateRequest.getUserId();
        String currency = transactionCreateRequest.getCurrency();
        String transactionType = transactionCreateRequest.getTransactionType();
        LocalDateTime createdAt = LocalDateTime.now();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (accountFrom == null || accountTo == null || accountFrom.equals(accountTo)) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same or null");
        }
        String id = UUID.randomUUID().toString();
        Transaction transactionEntity = TransactionMapper.toTransactionEntity(
                new TransactionResponseDto(
                        id,
                        userId,
                        accountTo,
                        accountFrom,
                        amount,
                        currency,
                        transactionType,
                        "PENDING",
                        createdAt
                )
        );

        return transactionRepository.save(transactionEntity);
    }

    public List<TransactionResponseDto> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findById(accountId).stream()
                .map(TransactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDto> getTransactionsByUserId(String userId) {
        return transactionRepository.findById(userId).stream()
                .map(TransactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
    }
    public List<TransactionResponseDto> getTransactionsByType(String userId, String type) {
        return transactionRepository.findByUserIdAndTransactionType(userId, type).stream()
                .map(TransactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
    }
    public List<TransactionResponseDto> getTransactionsByDateRange(String userId, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByUserIdAndCreatedAtBetween(userId, from, to).stream()
                .map(TransactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
    }
    }



