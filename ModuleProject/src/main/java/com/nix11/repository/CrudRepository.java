package com.nix11.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T t);

    void saveAll(List<T> t);

    void update(T t);

    void delete(String id);

    List<T> getAll();

    Optional<T> getById(String id);
}
