package by.du;

import by.du.model.Workout;
import by.du.repository.WorkoutDAO;
import by.du.util.DbConn;
import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

public class App5 {

    public static void main(String[] args) throws SQLException {
        Workout workout = new Workout
                (Workout.Sport.BOXING,
                        1.5,
                        LocalDateTime.of(2021, Month.APRIL, 5, 20, 30, 0),
                        true);

        System.out.println(workout);

        WorkoutDAO wdao = new WorkoutDAO();

        wdao.create(workout);//DAO не работает! Что делать ?

//        create(workout);

    }
//    statement.executeUpdate("create table tbl_for_workout(" +
//                "id int primary key auto_increment," +
//                "sport varchar(10) not null," +
//                "duration double not null," +
//                "begin timestamp not null," +
//                "isDone boolean not null)");

    private static final String query = "insert into tbl_for_workout(" +
            "sport,duration,begin,isDone) values (?,?,?,?)";

    public static void create(Workout workout) throws SQLException {
        Statement statement = DbConn.INSTANCE.getConnection().createStatement();
        statement.executeUpdate("use data");
        PreparedStatement ps = DbConn.INSTANCE.getConnection().prepareStatement(query);
        ps.setString(1, workout.getSport().toString());
        ps.setDouble(2, workout.getDuration());
        ps.setTimestamp(3, Timestamp.valueOf(workout.getBegin()));
        ps.setBoolean(4, workout.isDone());
    }
}
