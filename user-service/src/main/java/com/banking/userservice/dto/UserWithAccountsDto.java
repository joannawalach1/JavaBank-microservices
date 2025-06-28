package com.banking.userservice.dto;

import com.banking.accountservice.dto.AccountResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class UserWithAccountsDto {
        private String username;
        private String email;
        private List<AccountResponseDto> accounts;

    public UserWithAccountsDto(UserResponseDto user, List<AccountResponseDto> accounts) {
    }
}
