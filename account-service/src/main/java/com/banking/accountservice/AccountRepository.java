package com.banking.accountservice;

import com.banking.accountservice.dto.AccountResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    List<Account> findByUserId(Long userId);

    List<Account> findByAccountNumber(String accountNumber);

    List<AccountResponseDto> getAccountsByUserId(Long userId);
}