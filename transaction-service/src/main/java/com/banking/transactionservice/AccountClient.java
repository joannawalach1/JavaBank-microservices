package com.banking.transactionservice;

import com.banking.transactionservice.dto.AccountResponseDto;
import com.banking.transactionservice.dto.AccountUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "account-service", fallback = AccountClientFallback.class)
public interface AccountClient {

    @GetMapping("/api/accounts/user/{userId}")
    List<AccountResponseDto> getAccountByUserId(Long userId);

    @PutMapping("/api/accounts/{accountId}/balance")

        void updateBalance(@PathVariable("accountId") String accountId, @RequestBody AccountUpdateRequestDto accountUpdateRequestDto);
    }
