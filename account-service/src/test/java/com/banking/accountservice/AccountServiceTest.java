package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountService accountService;
    private InMemoryAccountRepository inMemoryAccountRepository;
    private AccountMapper accountMapper;
    private AccountResponseDto accountResponseDto;

    @BeforeEach
    public void setUp() {
        inMemoryAccountRepository = new InMemoryAccountRepository();
        accountMapper = new AccountMapperImpl();
        accountService = new AccountService(inMemoryAccountRepository, accountMapper);
        accountResponseDto = accountService.createAccount(new AccountCreateDto(1L, "PL61109010140000071219812874", "user123", "SAVINGS", new BigDecimal("1500.00"), "PLN", "Active"));
    }

    @Test
    public void should_create_account() {
        assertEquals(accountResponseDto.getAccountNumber(), "PL61109010140000071219812874");
    }

    @Test
    public void should_throw_exception_if_accountId_doesnt_exist() {
        Long accountId = null;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.getAccountById(accountId);
        });

        assertEquals("Account not found: null", exception.getMessage());
    }

//    @Test
//    public void should_return_accounts_when_userId_exists() {
//        String userId = accountResponseDto.getUserId();
//        Account accountsByUserId = accountService.getAccountsByUserId(userId);
//
//        assertNotNull(accountsByUserId, "Returned list should not be null");
//        assertEquals(userId, Long.valueOf(accountsByUserId.getUserId()));
//    }

    @Test
    public void should_return_account_when_accountNumber_exists() {
        String accountNumber = "PL61109010140000071219812874";
        Account accountsByAccountNumber = accountService.getAccountByNumber(accountNumber);

        assertNotNull(accountsByAccountNumber, "Returned list should not be null");
        assertEquals("PL61109010140000071219812874", accountsByAccountNumber.getAccountNumber());
    }

    @Test
    public void should_return_all_accounts_when_accounts_exist() {
        List<AccountResponseDto> allAccounts = accountService.getAllAccounts();
        assertNotNull(allAccounts, "List of all accounts should not be null");
        assertFalse(allAccounts.isEmpty(), "There should be at least one account");
    }
}