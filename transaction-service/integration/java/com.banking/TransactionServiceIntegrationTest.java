package com.banking;

import com.banking.transactionservice.TransactionServiceApplication;
import com.banking.transactionservice.configuration.CassandraConfig;
import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TransactionServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionServiceIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_create_new_transaction() {
        // 1. Tworzymy nową transakcję
        TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest(123L, "456", 789L, new BigDecimal("500.00"), "USD", "TRANSFER");
        ResponseEntity<TransactionResponseDto> registerResponse = restTemplate.postForEntity(
                "/api/transactions",
                transactionCreateRequest,
                TransactionResponseDto.class
        );

        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());
        TransactionResponseDto registeredTransaction = registerResponse.getBody();
        assertEquals(456L, registerResponse.getBody().getAccountId());
        assertEquals(123L, registerResponse.getBody().getUserId());

        // 2. Pobranie transakcji po ID
        String id = registeredTransaction.getAccountId();
        ResponseEntity<TransactionResponseDto> getByAccountIdResponse = restTemplate.getForEntity(
                "/api/transactions/" + id,
                TransactionResponseDto.class
        );
        assertEquals(HttpStatus.OK, getByAccountIdResponse.getStatusCode());
        String accountId = Objects.requireNonNull(getByAccountIdResponse.getBody()).getAccountId();
        assertNotNull(accountId);
    }
}
