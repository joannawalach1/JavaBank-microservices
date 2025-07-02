package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;

public class AccountMapperImpl implements AccountMapper {
    @Override
    public Account toAccountEntity(AccountCreateDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setUserId(dto.getUserId());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        account.setCurrency(dto.getCurrency());
        account.setStatus(dto.getStatus());
        return account;
    }

    @Override
    public AccountResponseDto toAccountResponseDto(Account account) {
        return new AccountResponseDto(
                account.getAccountNumber(),
                account.getUserId(),
                account.getAccountType(),
                account.getBalance(),
                account.getCurrency(),
                account.getStatus()
        );
    }
}
