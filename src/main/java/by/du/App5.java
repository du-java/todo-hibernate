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
import java.util.Optional;

public class App5 {

    public static void main(String[] args) throws SQLException {
        Workout workout = new Workout
                (Workout.Sport.SWIMMING,
                        1.8,
                        LocalDateTime.of(2021, Month.DECEMBER, 6, 21, 26, 3),
                        false);

        System.out.println(workout);
        System.out.println("->");


        WorkoutDAO wdao = new WorkoutDAO();
        wdao.create(workout);
        List<Workout> all1 = wdao.findAll();
        String s = showList(all1);
        System.out.println(s);
        Optional<Workout> workoutByID = wdao.findById(4);
        Workout workoutID4 = workoutByID.get();
        System.out.println("Get: " + workoutID4);
        Optional<Workout> optionalId1 = wdao.findById(1);
        Workout workoutId1 = optionalId1.get();
        wdao.delete(workoutId1);
        List<Workout> all2 = wdao.findAll();
        String s1 = showList(all2);
        System.out.println(s1);


    }

    public static <T> String showList(List<T> localList) {
        StringBuilder rtn = new StringBuilder();
        for (Object tmp : localList) {
            rtn.append(tmp).append("\n");
        }
        return rtn.toString();
    }


}
