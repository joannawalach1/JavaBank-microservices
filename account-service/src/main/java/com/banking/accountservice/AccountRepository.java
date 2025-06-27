package com.banking.accountservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    List<Account> findByUserId(String userId);

    List<Account> findByAccountNumber(String accountNumber);
}