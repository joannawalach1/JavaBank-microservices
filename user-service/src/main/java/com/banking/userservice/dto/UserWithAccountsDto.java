package com.banking.userservice.dto;

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

}
