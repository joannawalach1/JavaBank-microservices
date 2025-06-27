package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccountEntity(AccountCreateDto accountCreateDto);

    AccountResponseDto toAccountResponseDto(Account account);
}