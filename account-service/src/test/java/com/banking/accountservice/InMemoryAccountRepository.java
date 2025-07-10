package com.banking.accountservice;

import com.banking.accountservice.dto.AccountResponseDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<Long, Account> accounts = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    @Override
    public Optional<Account> findById(String id) {
        return accounts.values().stream()
                .filter(account -> id.equals(account.getId()))
                .findFirst();
    }

    @Override
    public Optional<Account> findByUserId(String userId) {
        return accounts.values().stream()
                .filter(account -> userId.equals(account.getUserId()))
                .findFirst();
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accounts.values().stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()))
                .findFirst();
    }

    @Override
    public Optional<Account> getAccountsByUserId(String userId) {
        return accounts.values().stream()
                .filter(account -> userId.equals(account.getUserId()))
                .findFirst();
    }

    @Override
    public Optional<Account> getAccountsByUserId(Long accountId) {
       return accounts.values().stream()
               .filter(account -> accountId.equals(account.getUserId()))
               .findFirst();
    }

    @Override
    public Optional<AccountResponseDto> getAccountById(Long accountId) {
        return Optional.empty();
    }

    @Override
    public <S extends Account> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Account> List<S> insert(Iterable<S> entities) {
        return null;
    }



    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }
    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return null;
    }
    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Account> S save(S entity) {
        accounts.put(Long.valueOf(entity.getId()), entity);
        return entity;
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return null;
    }



    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Account> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Account> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }
}
