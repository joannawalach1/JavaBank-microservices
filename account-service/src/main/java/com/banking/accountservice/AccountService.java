package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<AccountResponseDto> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).stream()
                .map(accountMapper::toAccountResponseDto)
                .collect(Collectors.toList());
    }

    public AccountResponseDto createAccount(AccountCreateDto accountCreateDto) {
        Account account = accountMapper.toAccountEntity(accountCreateDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(savedAccount);
    }

    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toAccountResponseDto)
                .collect(Collectors.toList());
    }
}