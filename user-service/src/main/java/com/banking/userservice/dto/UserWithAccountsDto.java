package com.banking.userservice.dto;

import com.banking.userservice.dto.AccountResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAccountsDto {
        private String username;
        private String email;
        private List<AccountResponseDto> accounts;

    public UserWithAccountsDto(UserResponseDto user, List<AccountResponseDto> accounts) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.accounts = accounts;
    }

}
