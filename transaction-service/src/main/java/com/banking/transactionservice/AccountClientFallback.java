package com.banking.transactionservice;

import com.banking.transactionservice.dto.AccountResponseDto;
import com.banking.transactionservice.dto.AccountUpdateRequestDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class AccountClientFallback implements AccountClient {

    @Override
    public List<AccountResponseDto> getAccountByUserId(Long userId) {
        System.out.println("Fallback triggered: account-service is unavailable [getAccountByUserId]");

        AccountResponseDto fallbackResponse = new AccountResponseDto();
        fallbackResponse.setAccountNumber("N/A");
        fallbackResponse.setUserId(String.valueOf(userId));
        fallbackResponse.setAccountType("N/A");
        fallbackResponse.setBalance(BigDecimal.ZERO);
        fallbackResponse.setCurrency("N/A");
        fallbackResponse.setStatus("Unavailable");

        return Collections.singletonList(fallbackResponse);
    }

    @Override
        public void updateBalance (String accountId, AccountUpdateRequestDto accountUpdateRequestDto){
            System.out.println("Fallback triggered: account-service is unavailable [updateBalance]");
            System.out.println("Nie udało się zaktualizować salda konta o ID: " + accountId);
        }
    }
