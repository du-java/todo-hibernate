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
    private String sport;
    @Column(name = "duration")
    private double duration;
    @Column(name = "begin")
    private LocalDateTime begin;
    @Column(name = "isDone")
    private boolean isDone;

    public Workout(String  sport, double duration, LocalDateTime begin, boolean isDone) {
        this.sport = sport;
        this.duration = duration;
        this.begin = begin;
        this.isDone = isDone;
    }


    public static final class Sport {

        public static final String BOXING = "BOXING";
        public static final String SWIMMING = "SWIMMING0";

    }
}

