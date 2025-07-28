package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import com.banking.userservice.dto.TransactionResponseDto;
import com.banking.userservice.dto.UserFullProfileDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FullProfileClient {

    private final UserRepository userRepository;
    private final AccountClient accountClient;
    private final TransactionClient transactionClient;

    public FullProfileClient(UserRepository userRepository, AccountClient accountClient, TransactionClient transactionClient) {
        this.userRepository = userRepository;
        this.accountClient = accountClient;
        this.transactionClient = transactionClient;
    }

    public UserFullProfileDto getUserFullProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username '" + username + "' was not found"));

        List<AccountResponseDto> accounts = accountClient.getAccountsForUser(user.getId());

        List<TransactionResponseDto> allTransactions = new ArrayList<>();

        if (accounts != null) {
            for (AccountResponseDto account : accounts) {
                List<TransactionResponseDto> accountTransactions = transactionClient.getUserFullProfile(account.getUserId());
                if (accountTransactions != null) {
                    allTransactions.addAll(accountTransactions);
                }
            }
        }

        return new UserFullProfileDto(user, accounts, allTransactions);
    }
}
