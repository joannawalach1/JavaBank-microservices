package com.banking.transactionservice;

import com.banking.transactionservice.dto.AccountUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {
    @PutMapping("/accounts/{accountId}/balance")

        void updateBalance(@PathVariable("accountId") String accountId, @RequestBody AccountUpdateRequestDto accountUpdateRequestDto);
    }
