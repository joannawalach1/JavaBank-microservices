package com.banking.transactionservice;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CassandraRepository<Transaction, String> {
    List<Transaction> findByUserIdAndTransactionType(String userId, String transactionType);
    List<Transaction> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime from, LocalDateTime to);
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByAccountId(String accountId);
}
