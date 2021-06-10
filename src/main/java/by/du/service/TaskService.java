package by.du.service;

import by.du.exception.NotFoundException;
import by.du.model.Task;
import by.du.repository.Dao;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService implements Service<Task> {
    private final Dao<Task> taskDao;

    public TaskService(Dao<Task> taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task findById(int id) {
        return taskDao.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public List<Task> findAllBetween(LocalDateTime from, LocalDateTime to) {
        return taskDao.findAllBetween(from, to);
    }

    @Override
    public Task create(Task task) {
        return taskDao.create(task);
    }

    @Override
    public Task update(Task task) {
        return taskDao.update(task);
    }

    @Override
    public void delete(Task task) {
        taskDao.delete(task);
    }
}
