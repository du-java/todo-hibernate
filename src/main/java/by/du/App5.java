package by.du;

import by.du.model.Workout;
import by.du.repository.Dao;
import by.du.repository.WorkoutDAO;
import by.du.service.WorkoutService;
import org.hibernate.Session;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class App5 {

    public static void main(String[] args) throws SQLException {
        Workout workout = new Workout
                (Workout.Sport.BOXING,
                        1.5,
                        LocalDateTime.of(2021, Month.APRIL, 5, 20, 30, 0),
                        true);

        System.out.println(workout);



        WorkoutService ws = new WorkoutService();




    }

    public static <T> String showList(List<T> localList) {
        StringBuilder rtn = new StringBuilder();
        for (Object tmp : localList) {
            rtn.append(tmp).append("\n");
        }
        return rtn.toString();
    }

    public static WorkoutService getWorkoutService(Session session){
        final Dao<Workout> workoutDao = new WorkoutDAO(session);
    }
}
