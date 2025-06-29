package com.banking.userservice;

import com.banking.accountservice.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final RestTemplate restTemplate;

    @Value("${account.service.url:http://localhost:8081}")
    private String accountServiceUrl;

    public List<AccountResponseDto> getAccountsByUserId(Long userId) {
        String url = accountServiceUrl + "/api/accounts/user/" + userId;
        ResponseEntity<List<AccountResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountResponseDto>>() {
                }
        );

        return response.getBody();
    }

    public AccountResponseDto getAccountById(String accountId) {
        String url = accountServiceUrl + "/api/accounts/" + accountId;
        return restTemplate.getForObject(url, AccountResponseDto.class);
    }
}