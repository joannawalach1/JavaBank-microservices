package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "account-service", fallback = AccountClientFallback.class)
public interface AccountClient {
    @GetMapping("/api/accounts/{userId}")
    List<AccountResponseDto> getAccountsForUser(@PathVariable("userId") Long userId);

}
