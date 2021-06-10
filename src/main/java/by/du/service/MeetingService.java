package by.du.service;

import by.du.exception.NotFoundException;
import by.du.model.Meeting;
import by.du.repository.Dao;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingService implements Service<Meeting> {

    private final Dao<Meeting> meetingDao;

    @Override
    public Meeting findById(int id) {
        return meetingDao.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<Meeting> findAll() {
        return meetingDao.findAll();
    }

    @Override
    public List<Meeting> findAllBetween(LocalDateTime from, LocalDateTime to) {
        return meetingDao.findAllBetween(from, to);
    }

    @Override
    public Meeting create(Meeting meeting) {
        return meetingDao.create(meeting);
    }

    @Override
    public Meeting update(Meeting meeting) {
        return meetingDao.update(meeting);
    }

    @Override
    public void delete(Meeting meeting) {
        meetingDao.delete(meeting);
    }

    public MeetingService(Dao<Meeting> meetingDao) {
        this.meetingDao = meetingDao;
    }
}
