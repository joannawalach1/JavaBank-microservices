package com.banking.transactionservice;

import com.banking.transactionservice.dto.AccountUpdateRequestDto;
import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import com.banking.transactionservice.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceApplicationTest {
    private InMemoryTransactionRepository inMemoryTransactionRepository;
    private TransactionService transactionService;
    private AccountClient accountClient;
    private TransactionCreateRequest request;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        inMemoryTransactionRepository = new InMemoryTransactionRepository();
        accountClient = mock(AccountClient.class);
        doNothing().when(accountClient).updateBalance(anyString(), any(AccountUpdateRequestDto.class));
        transactionService = new TransactionService(inMemoryTransactionRepository, accountClient);
        request = new TransactionCreateRequest(
                1L,
                "ACC123",
                987654321L,
                new BigDecimal("100.50"),
                "PLN",
                TransactionType.DEPOSIT.name());
        transaction = transactionService.createTransaction(request);

    }
    @Test
    public void shouldCreateTransactionSuccessfully() {
        assertNotNull(transaction);
        assertEquals(1L, transaction.getUserId());
        assertEquals(TransactionType.DEPOSIT.name(), transaction.getTransactionType());
        assertNotNull(transaction.getId());
    }

    @Test
    public void shouldReturnTransactionResponseDtoById() {
        TransactionResponseDto dto = transactionService.getTransactionsById(transaction.getId());
        assertNotNull(dto);
        assertEquals(transaction.getId(), dto.getId());
        assertEquals("ACC123", dto.getAccountId());
    }

    @Test
    public void shouldThrowTransactionNotFoundException() {
        UUID notExistingId = UUID.randomUUID();
        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionsById(notExistingId);
        });

        assertEquals("Transaction not found with id: " + notExistingId, exception.getMessage());
    }
}