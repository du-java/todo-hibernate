package by.du.repository;

import by.du.exception.DbException;
import by.du.model.Meeting;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MeetingDao2 extends AbstractDao<Meeting> {

    private static final String SELECT_BY_ID = "select * from todo where id = ?";
    private static final String SELECT_ALL = "select * from todo";
    private static final String SELECT_BETWEEN = "select * from todo where start_time between ? and ?";

    private static final String INSERT = "insert into todo (place, description, start_time) VALUES(?, ?, ?)";
    private static final String DELETE = "delete from todo where id = ?";
    private static final String UPDATE = "update todo set place=?, description=?, start_time=? where id = ?";

    public MeetingDao2() {
        super(Meeting.class);
    }

    @Override
    protected String getSqlSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSqlSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSqlSelectAllBetween() {
        return SELECT_BETWEEN;
    }

    @Override
    protected String getSqlInsert() {
        return INSERT;
    }

    @Override
    protected String getSqlDelete() {
        return DELETE;
    }

    @Override
    protected String getSqlUpdate() {
        return UPDATE;
    }

    @Override
    protected List<Meeting> findAll(ResultSet rs) throws SQLException {
        final ArrayList<Meeting> meetings = new ArrayList<>();
        while (rs.next()) {
            meetings.add(buildMeeting(rs));
        }
        return meetings;
    }

    @Override
    protected Optional<Meeting> findById(PreparedStatement st) throws SQLException {
        try (final ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return Optional.of(buildMeeting(rs));
            }
            return Optional.empty();
        }
    }

    @Override
    protected Meeting create(PreparedStatement st, Meeting meeting) throws SQLException {
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
    }

    @Override
    protected Meeting update(PreparedStatement st, Meeting meeting) throws SQLException {
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
    }

    private Meeting buildMeeting(ResultSet rs) throws SQLException {
        return Meeting.builder()
                .id(rs.getInt("id"))
                .place(rs.getString("place"))
                .desc(rs.getString("description"))
                .time(rs.getTimestamp("start_time").toLocalDateTime())
                .build();
    }
}
