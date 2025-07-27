package com.banking.transactionservice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements TransactionRepository {
    private Map<UUID, Transaction> storage = new HashMap<>();

    @Override
    public Optional<List<Transaction>> findByUserIdAndTransactionType(String userId) {
        return Optional.of(
                storage.values().stream()
                        .filter(t -> t.getUserId().toString().equals(userId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<Transaction>> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime from, LocalDateTime to) {
        return Optional.of(
                storage.values().stream()
                        .filter(t -> t.getUserId().toString().equals(userId) &&
                                t.getCreatedAt().isAfter(from) &&
                                t.getCreatedAt().isBefore(to))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<Transaction>> findByUserId(String userId) {
        return Optional.of(
                storage.values().stream()
                        .filter(t -> t.getUserId().toString().equals(userId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<Transaction>> findByAccountId(String accountId) {
        return Optional.of(
                storage.values().stream()
                        .filter(t -> t.getAccountId().equals(accountId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Transaction> findById(UUID transactionId) {
        return Optional.ofNullable(storage.get(transactionId));
    }

    @Override
    public <S extends Transaction> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Transaction> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            save(entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public Optional<Transaction> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Transaction> findAllById(Iterable<Long> longs) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void deleteById(Long aLong) {}

    @Override
    public void delete(Transaction entity) {
        storage.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {}

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {
        for (Transaction entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public Slice<Transaction> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Transaction> S insert(S entity) {
        return save(entity);
    }

    @Override
    public <S extends Transaction> List<S> insert(Iterable<S> entities) {
        return saveAll(entities);
    }
}
