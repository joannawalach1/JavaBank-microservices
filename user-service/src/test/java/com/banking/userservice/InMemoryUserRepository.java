package com.banking.userservice;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {

        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public <S extends User> S save(S entity) {
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            Long userId = Long.valueOf(id);
            return Optional.ofNullable(users.get(userId));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(String id) {
        try {
            Long userId = Long.valueOf(id);
            return users.containsKey(userId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> findAllById(Iterable<String> ids) {
        List<User> result = new ArrayList<>();
        for (String id : ids) {
            findById(id).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void deleteById(String id) {
        try {
            Long userId = Long.valueOf(id);
            users.remove(userId);
        } catch (NumberFormatException ignored) {
        }
    }

    @Override
    public void delete(User entity) {
        users.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        for (User user : entities) {
            users.remove(user.getId());
        }
    }

    @Override
    public void deleteAll() {
        users.clear();
    }


    @Override public void flush() {}
    @Override public <S extends User> S saveAndFlush(S entity) { return save(entity); }
    @Override public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) { return saveAll(entities); }
    @Override public void deleteAllInBatch(Iterable<User> entities) {}
    @Override public void deleteAllByIdInBatch(Iterable<String> ids) {}
    @Override public void deleteAllInBatch() {}
    @Override public User getOne(String s) { return null; }
    @Override public User getById(String s) { return null; }
    @Override public User getReferenceById(String s) { return null; }
    @Override public <S extends User> Optional<S> findOne(Example<S> example) { return Optional.empty(); }
    @Override public <S extends User> List<S> findAll(Example<S> example) { return List.of(); }
    @Override public <S extends User> List<S> findAll(Example<S> example, Sort sort) { return List.of(); }
    @Override public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) { return Page.empty(); }
    @Override public <S extends User> long count(Example<S> example) { return 0; }
    @Override public <S extends User> boolean exists(Example<S> example) { return false; }
    @Override public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) { return null; }
    @Override public List<User> findAll(Sort sort) { return findAll(); }
    @Override public Page<User> findAll(Pageable pageable) { return Page.empty(); }
}