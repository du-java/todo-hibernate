package by.du;

import by.du.exception.NotFoundException;
import by.du.model.Meeting;
import by.du.repository.MeetingDao;
import by.du.service.MeetingService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


@Slf4j
public class App {
    public static void main(String[] args) {
        final MeetingService meetingService = getService();

        try {
            meetingService.findById(999);
        } catch (NotFoundException ex) {
            log.error("not found meeting[{}]", ex.getId());
        }

//        meetingService.create(Meeting.builder()
//                .place("place1")
//                .desc("desc1")
//                .time(LocalDateTime.now())
//                .build());

        final Meeting meeting2 = meetingService.findById(2);
        meeting2.setDesc("desc2");
        meeting2.setPlace("place2");
        meetingService.update(meeting2);

        meetingService.create(Meeting.builder()
                .place("place1")
                .desc("desc1")
                .time(LocalDateTime.now())
                .build());

        meetingService.delete(Meeting.builder().id(1).build());

        System.out.println(meetingService.findAll());
    }

    private static MeetingService getService() {
        final MeetingDao meetingDao = new MeetingDao();
        final MeetingService meetingService = new MeetingService(meetingDao);
        return meetingService;
    }
}
