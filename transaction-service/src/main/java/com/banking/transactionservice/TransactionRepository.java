package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionResponseDto;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CassandraRepository<Transaction, Long> {
    Optional<List<Transaction>> findByUserIdAndTransactionType(String userId);
    Optional<List<Transaction>> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime from, LocalDateTime to);
    Optional<List<Transaction>> findByUserId(String userId);
    Optional<List<Transaction>> findByAccountId(String accountId);

    Optional<Transaction> findById(UUID transactionId);
}
