package com.banking.transactionservice;

import com.banking.transactionservice.dto.AccountUpdateRequestDto;
import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import com.banking.transactionservice.exceptions.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionService(TransactionRepository transactionRepository, AccountClient accountClient) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
    }

    public TransactionResponseDto getTransactionsById(UUID transactionId) {
        Transaction byId = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + transactionId));
        return TransactionMapper.toTransactionResponseDto(byId);
    }

    public Transaction createTransaction(TransactionCreateRequest transactionCreateRequest) {
        BigDecimal amount = transactionCreateRequest.getAmount();
        Long accountFrom = transactionCreateRequest.getAccountFrom();
        String accountTo = transactionCreateRequest.getAccountId();
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
        UUID id = UUID.randomUUID();
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

        Transaction savedTransaction = transactionRepository.save(transactionEntity);
        AccountUpdateRequestDto request = new AccountUpdateRequestDto(
                transactionCreateRequest.getAmount(),
                transactionCreateRequest.getTransactionType(),
                transactionCreateRequest.getAccountId()
        );

        accountClient.updateBalance(transactionCreateRequest.getAccountId(), request);
        return savedTransaction;
    }

    public List<Transaction> getTransactionsByType(String userId) {
        return transactionRepository.findByUserIdAndTransactionType(userId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found: " + userId));
    }

    public List<Transaction> getTransactionsByDateRange(String userId, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByUserIdAndCreatedAtBetween(userId, from, to)
                .orElseThrow(() -> new TransactionNotFoundException("User not found: " + userId));
    }

    public List<Transaction> getTransactionsByUserId(String id) {
        return transactionRepository.findByUserId(id)
                .orElseThrow(() -> new TransactionNotFoundException("User not found: " + id));
    }

    public List<TransactionResponseDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toTransactionResponseDto)
                .collect(Collectors.toList());
    }
}



