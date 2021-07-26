package by.du.service;

import by.du.exception.NotFoundException;
import by.du.model.Workout;
import by.du.repository.Dao;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutService implements Service<Workout> {

    private final Dao<Workout> workoutDao;

    public WorkoutService(Dao<Workout> workoutDao) {
        this.workoutDao = workoutDao;
    }





    @Override
    public Workout findById(int id) {
         return workoutDao.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<Workout> findAll() {
        return workoutDao.findAll();
    }

    @Override
    public List<Workout> findAllBetween(LocalDateTime from, LocalDateTime to) {
        return workoutDao.findAllBetween(from,to);
    }

    @Override
    public Workout create(Workout workout) {
        return workoutDao.create(workout);
    }

    @Override
    public Workout update(Workout workout) {
        return workoutDao.update(workout);
    }

    @Override
    public void delete(Workout workout) {
        workoutDao.delete(workout);
    }


}
