package by.du.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_todo_meeting")
public class Meeting implements Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "meeting_timestamp")
    private LocalDateTime time;
    @Column(length = 100, name = "meeting_desc")
    private String desc;
    @Column(length = 50, name = "meeting_place")
    private String place;
}
