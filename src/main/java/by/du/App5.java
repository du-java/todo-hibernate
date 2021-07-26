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
        final WorkoutService ws = getWorkouService(WorkoutDAO.session);
        Workout workout = new Workout
                (Workout.Sport.BOXING,
                        4,
                        LocalDateTime.of(2021, Month.DECEMBER, 6, 21, 26, 3),
                        true);

        System.out.println(workout);
        System.out.println("->");

        //                                                               ___
//        WorkoutDAO wdao = new WorkoutDAO();                         \
//        wdao.create(workout);                                       |
//        List<Workout> all1 = wdao.findAll();                        |
//        String s = showList(all1);                                  |
//        System.out.println(s);                                      |
//        Optional<Workout> workoutByID = wdao.findById(4);            \
//        Workout workoutID4 = workoutByID.get();                        >  Работает ( Только ДАО )
//        System.out.println("Get: " + workoutID4);                     /
//        Optional<Workout> optionalId1 = wdao.findById(1);            |
//        Workout workoutId1 = optionalId1.get();                      |
//        wdao.delete(workoutId1);                                     |
//        List<Workout> all2 = wdao.findAll();                         /
//        String s1 = showList(all2);                                /
//        System.out.println(s1);                                 __

        //SERVICE -> -> ->
        ws.create(workout);
        List<Workout> all = ws.findAll();
        System.out.println(showList(all));
        Workout byId = ws.findById(4);
        ws.delete(byId);
        List<Workout> all1 = ws.findAll();
        System.out.println(showList(all1));

    }

    public static <T> String showList(List<T> localList) {
        StringBuilder rtn = new StringBuilder();
        for (Object tmp : localList) {
            rtn.append(tmp).append("\n");
        }
        return rtn.toString();
    }

    public static WorkoutService getWorkouService(Session session) {
        final Dao<Workout> workoutDao = new WorkoutDAO(session);
        return new WorkoutService(workoutDao);

    }


}
