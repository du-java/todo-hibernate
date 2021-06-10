package by.du.repository;

import by.du.exception.DbException;
import by.du.model.Task;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TaskDao2 extends AbstractDao<Task> {

    private static final String SELECT_BY_ID = "select * from task where id = ?";
    private static final String SELECT_ALL = "select * from task";
    private static final String SELECT_BETWEEN = "select * from task where start_date between ? and ?";

    private static final String INSERT = "insert into task (isDone, description, start_date) VALUES(?, ?, ?)";
    private static final String DELETE = "delete from task where id = ?";
    private static final String UPDATE = "update task set isDone=?, description=?, start_date=? where id = ?";

    public TaskDao2() {
        super(Task.class);
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
    protected Optional<Task> findById(PreparedStatement st) throws SQLException {
        try (final ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return Optional.of(buildTask(rs));
            }
            return Optional.empty();
        }
    }

    @Override
    protected List<Task> findAll(ResultSet rs) throws SQLException {
        final ArrayList<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            tasks.add(buildTask(rs));
        }
        return tasks;
    }

    @Override
    protected Task create(PreparedStatement st, Task task) throws SQLException {
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
    }

    @Override
    protected Task update(PreparedStatement st, Task task) throws SQLException {
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
    }

    private Task buildTask(ResultSet rs) throws SQLException {
        return Task.builder()
                .id(rs.getInt("id"))
                .isDone(rs.getBoolean("isDone"))
                .desc(rs.getString("description"))
                .date(rs.getDate("start_date").toLocalDate())
                .build();
    }
}
