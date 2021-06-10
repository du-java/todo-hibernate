package by.du.repository;

import by.du.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Event> {

    Optional<T> findById(int id);

    List<T> findAll();

    List<T> findAllBetween(LocalDateTime from, LocalDateTime to);

    T create(T t);

    void delete(T t);

    T update(T t);
}
