package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import com.banking.accountservice.dto.AccountUpdateRequestDto;
import com.banking.accountservice.exceptions.AccountNotFound;
import com.banking.accountservice.exceptions.DuplicateAccountNumberException;
import com.banking.accountservice.exceptions.InvalidCurrencyException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Cacheable(value = "account", key = "#userId")
    public List<AccountResponseDto> getUserAccount(String userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(accountMapper::toAccountResponseDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "account", key = "#accountNumber")
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFound("Account not found: " + accountNumber));
    }

    public AccountResponseDto createAccount(AccountCreateDto accountCreateDto) {
        Optional<Account> existingAccount = accountRepository.findByAccountNumber(accountCreateDto.getAccountNumber());
        if (existingAccount.isPresent()) {
            throw new DuplicateAccountNumberException("Account number" + accountCreateDto.getAccountNumber() + "already exists");
        }
        List<String> allowed = List.of("PLN", "EUR", "USD");
        if (!allowed.contains(accountCreateDto.getCurrency())) {
            throw new InvalidCurrencyException("Unsupported currency: " + accountCreateDto.getCurrency());
        }

        Account account = accountMapper.toAccountEntity(accountCreateDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(savedAccount);
    }

    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toAccountResponseDto)
                .collect(Collectors.toList());
    }

    public List<AccountResponseDto> getAccountsByUserId(String userId) {
        return accountRepository.getAccountsByUserId(userId)
                .orElseThrow(() -> new AccountNotFound("Account not found: "));
    }

    public AccountResponseDto getAccountById(Long accountId)  {
    return accountRepository.getAccountById(accountId)
            .orElseThrow(() -> new AccountNotFound("Account not found: " + accountId));
}

    @Transactional
    public void updateBalance(String accountId, AccountUpdateRequestDto accountUpdateRequestDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFound("Account not found: " + accountId));

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(accountUpdateRequestDto.getAmount());
        ;

        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}