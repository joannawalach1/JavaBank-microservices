package com.banking.accountservice;

import com.banking.accountservice.dto.AccountResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByUserId(String userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> getAccountsByUserId(String userId);

    Optional<Account> getAccountsByUserId(Long accountId);

    Optional<AccountResponseDto> getAccountById(Long accountId);
}