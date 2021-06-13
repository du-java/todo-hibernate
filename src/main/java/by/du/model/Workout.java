package by.du.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_for_workout")
public class Workout implements Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sport")
    private Sport sport;
    @Column(name = "duration")
    private double duration;
    @Column(name = "begin")
    private LocalDateTime begin;
    @Column(name = "isDone")
    private boolean isDone;

    public Workout(Sport sport, double duration, LocalDateTime begin, boolean isDone) {
        this.sport = sport;
        this.duration = duration;
        this.begin = begin;
        this.isDone = isDone;
    }

    @Override
    public Integer getId() {
        return null;
    }

    public enum Sport {
        BOXING("Boxing"),
        SWIMMING("Swimming");

        private String name;

        Sport(String name) {
            this.name = name;
        }
    }

//    public static void main(String[] args) throws SQLException {

//        Statement statement = DbConn.INSTANCE.getConnection().createStatement();
//        statement.executeUpdate("drop table tbl_for_workout");
//        statement.executeUpdate("create table tbl_for_workout(" +
//                "id int primary key auto_increment," +
//                "sport varchar(10) not null," +
//                "duration double not null," +
//                "begin timestamp not null," +
//                "isDone boolean not null)");
//    }
}
