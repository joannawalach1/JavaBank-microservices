package com.banking.userservice;

import com.banking.accountservice.dto.AccountResponseDto;
import com.banking.userservice.dto.UserFullProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AccountClient {
    private final RestTemplate restTemplate;

    public List<AccountResponseDto> getAccountsForUser(Long userId) {
        String url = "http://account-service/api/accounts/user/" + userId;
        ResponseEntity<AccountResponseDto[]> response =
                restTemplate.getForEntity(url, AccountResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
