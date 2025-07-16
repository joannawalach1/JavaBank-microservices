package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class AccountClientFallback implements AccountClient {

    @Override
        public List<AccountResponseDto> getAccountsForUser(Long userId) {
            System.out.println("Fallback triggered: account-service is unavailable [getAccountsForUser]");

            AccountResponseDto fallbackResponse = new AccountResponseDto();
            fallbackResponse.setAccountNumber("N/A");
            fallbackResponse.setUserId(String.valueOf(userId));
            fallbackResponse.setAccountType("N/A");
            fallbackResponse.setBalance(BigDecimal.ZERO);
            fallbackResponse.setCurrency("N/A");
            fallbackResponse.setStatus("N/A");

            return Collections.singletonList(fallbackResponse);
        }
    }