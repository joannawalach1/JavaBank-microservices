package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "account-service")
public interface AccountClient {
   @GetMapping
    public List<AccountResponseDto> getAccountsForUser(Long userId);
}
