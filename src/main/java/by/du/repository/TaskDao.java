package by.du.repository;

import by.du.exception.DbException;
import by.du.model.Task;
import by.du.util.DbConn;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TaskDao implements Dao<Task> {

    private static final String SELECT_BY_ID = "select * from task where id = ?";
    private static final String SELECT_ALL = "select * from task";
    private static final String SELECT_BETWEEN = "select * from task where start_date between ? and ?";

    private static final String INSERT = "insert into task (isDone, description, start_date) VALUES(?, ?, ?)";
    private static final String DELETE = "delete from task where id = ?";
    private static final String UPDATE = "update task set isDone=?, description=?, start_date=? where id = ?";

    @Override
    public Optional<Task> findById(int id) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            try (final ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Task.builder()
                            .id(rs.getInt("id"))
                            .isDone(rs.getBoolean("isDone"))
                            .desc(rs.getString("description"))
                            .date(rs.getDate("start_date").toLocalDate())
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
    public List<Task> findAll() {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            try (final ResultSet rs = st.executeQuery()) {
                final ArrayList<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    tasks.add(Task.builder()
                            .id(rs.getInt("id"))
                            .isDone(rs.getBoolean("isDone"))
                            .desc(rs.getString("description"))
                            .date(rs.getDate("start_date").toLocalDate())
                            .build());
                }
                return tasks;
            }
        } catch (SQLException ex) {
            log.error("Error finding all events", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public List<Task> findAllBetween(LocalDateTime from, LocalDateTime to) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_BETWEEN)) {
            st.setDate(1, Date.valueOf(from.toLocalDate()));
            st.setDate(2, Date.valueOf(to.toLocalDate()));
            try (final ResultSet rs = st.executeQuery()) {
                final ArrayList<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    tasks.add(Task.builder()
                            .id(rs.getInt("id"))
                            .isDone(rs.getBoolean("isDone"))
                            .desc(rs.getString("description"))
                            .date(rs.getDate("start_date").toLocalDate())
                            .build());
                }
                return tasks;
            }
        } catch (SQLException ex) {
            log.error("Error finding all events between", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public Task create(Task task) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            st.setBoolean(1, task.getIsDone());
            st.setString(2, task.getDesc());
            st.setDate(3, Date.valueOf(task.getDate()));
            st.executeUpdate();
            try (final ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    final int id = keys.getInt(1);
                    task.setId(id);
                    return task;
                }
                log.error("Error while creating task [{}]", task);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while creating task", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public void delete(Task task) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(DELETE)) {
            st.setInt(1, task.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while deleting task [{}]", task);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while deleting task", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public Task update(Task task) {
        final Connection connection = DbConn.INSTANCE.getConnection();
        try (final PreparedStatement st = connection.prepareStatement(UPDATE)) {
            st.setBoolean(1, task.getIsDone());
            st.setString(2, task.getDesc());
            st.setDate(3, Date.valueOf(task.getDate()));
            st.setInt(4, task.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while updating task [{}]", task);
                throw new DbException();
            }
            return task;
        } catch (SQLException ex) {
            log.error("Error while updating task", ex);
            throw new DbException(ex);
        }
    }

}
