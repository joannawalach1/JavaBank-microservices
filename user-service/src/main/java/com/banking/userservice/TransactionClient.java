package com.banking.userservice;

import com.banking.transactionservice.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transaction-service")
public interface TransactionClient {
    @GetMapping
    public Transaction[] getUserFullProfile(String username);
}