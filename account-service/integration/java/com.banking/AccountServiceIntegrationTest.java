package com.banking;

import com.banking.accountservice.AccountServiceApplication;
import com.banking.accountservice.AccountStatus;
import com.banking.accountservice.AccountType;
import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AccountServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_create_new_account() {
        // 1. Tworzenie nowego konta
        AccountCreateDto accountCreateDto = new AccountCreateDto(null, "1234567890123456", "123L", AccountType.SAVINGS, new BigDecimal("1000.00"), "USD", AccountStatus.ACTIVE);
        ResponseEntity<AccountResponseDto> registerResponse = restTemplate.postForEntity(
                "/api/accounts",
                accountCreateDto,
                AccountResponseDto.class
        );

        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());
        AccountResponseDto registeredAccount = registerResponse.getBody();
        assertEquals("1234567890123456", registerResponse.getBody().getAccountNumber());
        assertEquals("123L", registerResponse.getBody().getUserId());

        // 2. Pobranie listy kont użytkownika

        String id = registeredAccount.getUserId();
        ResponseEntity<AccountResponseDto> getByUserIdResponse = restTemplate.getForEntity(
                "/api/accounts/user/" + id,
                AccountResponseDto.class
        );
        assertEquals(HttpStatus.OK, getByUserIdResponse.getStatusCode());
        String userId = Objects.requireNonNull(getByUserIdResponse.getBody()).getUserId();
        assertNotNull(userId);

        // 3. Aktualizacja danych konta
        // 4. Pobranie konta po numerze konta
        String accountNumber = registeredAccount.getAccountNumber();
        ResponseEntity<AccountResponseDto> response = restTemplate.getForEntity(
                "/api/accounts/number/" + accountNumber,
                AccountResponseDto.class
        );
        AccountResponseDto account = response.getBody();
        assertNotNull(account);
        assertEquals(accountNumber, account.getAccountNumber());

    }
}
