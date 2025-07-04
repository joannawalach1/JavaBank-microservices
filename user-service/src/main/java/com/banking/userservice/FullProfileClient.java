package com.banking.userservice;

import com.banking.accountservice.Account;
import com.banking.transactionservice.Transaction;
import com.banking.userservice.dto.UserFullProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FullProfileClient {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public FullProfileClient(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public UserFullProfileDto getUserFullProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' was not found"));
        ResponseEntity<Account[]> accounts = restTemplate.getForEntity(
                "http://account-service/api/accounts/" + user.getId(),
                Account[].class);
        Account[] body = accounts.getBody();

        List<Transaction> allTransactions = new ArrayList<>();

        if (accounts != null) {
            for (Account account : body) {
                Transaction[] accountTransactions = restTemplate.getForObject(
                        "http://transaction-service/transactions/by-account/" + account.getId(),
                        Transaction[].class);
                if (accountTransactions != null) {
                    allTransactions.addAll(Arrays.asList(accountTransactions));
                }
            }
        }

        return new UserFullProfileDto(user, accounts, allTransactions);
    }
}
