package by.du.repository;

import by.du.model.Meeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MeetingDao3 implements Dao<Meeting> {

    private final Session session;

    @Override
    public Optional<Meeting> findById(int id) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Optional<Meeting> meeting = Optional.ofNullable(session.get(Meeting.class, id));
            transaction.commit();
            return meeting;
        } catch (Exception ex) {
            transaction.rollback();
            return Optional.empty();
        }
    }

    @Override
    public List<Meeting> findAll() {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<Meeting> query = session.createQuery("from Meeting", Meeting.class);
            final List<Meeting> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Meeting> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Transaction transaction = session.beginTransaction();
        try {
            final Query<Meeting> query = session.createQuery("from Meeting m where m.time between :from and :to", Meeting.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            final List<Meeting> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public Meeting create(Meeting meeting) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(meeting);
            transaction.commit();
            return meeting;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public void delete(Meeting meeting) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.remove(meeting);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public Meeting update(Meeting meeting) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.persist(meeting);
            transaction.commit();
            return meeting;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        }
    }
}
