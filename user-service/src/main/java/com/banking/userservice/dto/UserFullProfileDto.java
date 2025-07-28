package com.banking.userservice.dto;

import com.banking.userservice.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullProfileDto {
    private Long id;
    private String name;
    private String email;
    private List<AccountResponseDto> accounts;
    private List<TransactionResponseDto> transactions;


    public UserFullProfileDto(User user, List<AccountResponseDto> accountsResponse, List<TransactionResponseDto> allTransactions) {
        this.id = user.getId();
        this.name = user.getFirstName() + " " + user.getSurname();
        this.email = user.getEmail();

        this.accounts = accountsResponse != null ? accountsResponse : Collections.emptyList();
        this.transactions = allTransactions != null ? allTransactions : Collections.emptyList();
    }
}
