package by.du.repository;

import by.du.exception.DbException;
import by.du.model.Meeting;
import by.du.util.DbConn;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MeetingDao implements Dao<Meeting> {

    private static final String SELECT_BY_ID = "select * from todo where id = ?";
    private static final String SELECT_ALL = "select * from todo";
    private static final String SELECT_BETWEEN = "select * from todo where start_time between ? and ?";

    private static final String INSERT = "insert into todo (place, description, start_time) VALUES(?, ?, ?)";
    private static final String DELETE = "delete from todo where id = ?";
    private static final String UPDATE = "update todo set place=?, description=?, start_time=? where id = ?";

    @Override
    public Optional<Meeting> findById(int id) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            try (final ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Meeting.builder()
                            .id(rs.getInt("id"))
                            .place(rs.getString("place"))
                            .desc(rs.getString("description"))
                            .time(rs.getTimestamp("start_time").toLocalDateTime())
                            .build());
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            log.error("Error finding event", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public List<Meeting> findAll() {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            try (final ResultSet rs = st.executeQuery()) {
                final ArrayList<Meeting> meetings = new ArrayList<>();
                while (rs.next()) {
                    meetings.add(Meeting.builder()
                            .id(rs.getInt("id"))
                            .place(rs.getString("place"))
                            .desc(rs.getString("description"))
                            .time(rs.getTimestamp("start_time").toLocalDateTime())
                            .build());
                }
                return meetings;
            }
        } catch (SQLException ex) {
            log.error("Error finding all events", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public List<Meeting> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_BETWEEN)) {
            st.setTimestamp(1, Timestamp.valueOf(from));
            st.setTimestamp(2, Timestamp.valueOf(to));
            try (final ResultSet rs = st.executeQuery()) {
                final ArrayList<Meeting> meetings = new ArrayList<>();
                while (rs.next()) {
                    meetings.add(Meeting.builder()
                            .id(rs.getInt("id"))
                            .place(rs.getString("place"))
                            .desc(rs.getString("description"))
                            .time(rs.getTimestamp("start_time").toLocalDateTime())
                            .build());
                }
                return meetings;
            }
        } catch (SQLException ex) {
            log.error("Error finding all events between", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public Meeting create(Meeting meeting) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, meeting.getPlace());
            st.setString(2, meeting.getDesc());
            st.setTimestamp(3, Timestamp.valueOf(meeting.getTime()));
            st.executeUpdate();
            try (final ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    final int id = keys.getInt(1);
                    meeting.setId(id);
                    return meeting;
                }
                log.error("Error while creating meeting [{}]", meeting);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while creating meeting", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public void delete(Meeting meeting) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(DELETE)) {
            st.setInt(1, meeting.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while deleting meeting [{}]", meeting);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while deleting meeting", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public Meeting update(Meeting meeting) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(UPDATE)) {
            st.setString(1, meeting.getPlace());
            st.setString(2, meeting.getDesc());
            st.setTimestamp(3, Timestamp.valueOf(meeting.getTime()));
            st.setInt(4, meeting.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while updating meeting [{}]", meeting);
                throw new DbException();
            }
            return meeting;
        } catch (SQLException ex) {
            log.error("Error while updating meeting", ex);
            throw new DbException(ex);
        }
    }
}
