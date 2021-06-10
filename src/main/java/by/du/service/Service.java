package by.du.service;

import by.du.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface Service<T extends Event> {

    T findById(int id);

    List<T> findAll();

    List<T> findAllBetween(LocalDateTime from, LocalDateTime to);

    T create(T t);

    T update(T t);

    void delete(T t);
}
