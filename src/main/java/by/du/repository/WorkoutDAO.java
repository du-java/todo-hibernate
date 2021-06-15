package by.du.repository;


import by.du.model.Workout;
import by.du.util.HibernateConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class WorkoutDAO implements Dao<Workout> {

    private final Session session = HibernateConfig.getSessionForWorkout();

    public WorkoutDAO(Session session) {
    }

    @Override
    public  Optional<Workout> findById(int id) {
        Transaction transaction = session.beginTransaction();
        try {
            Optional<Workout> optional = Optional.ofNullable(session.get(Workout.class, id));
            transaction.commit();
            return optional;
        } catch (Exception e) {
            transaction.rollback();
            return Optional.empty();
        }
    }

    @Override
    public List<Workout> findAll() {
        Transaction transaction = session.beginTransaction();
        try {
            final Query<Workout> query =
                    session.createQuery("from Workout", Workout.class);
            List<Workout> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.commit();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Workout> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<Workout> query = session.createQuery
                    ("from Workout m where m.time between :from and :to", Workout.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            final List<Workout> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public Workout create(Workout workout) {
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(workout);
            transaction.commit();
            return workout;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void delete(Workout workout) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(workout);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Workout update(Workout workout) {
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(workout);
            transaction.commit();
            return workout;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }




//    private Integer id;
//    private Workout.Sport sport;
//    private String duration;
//    private LocalDateTime begin;
//    private boolean isDone;
}
