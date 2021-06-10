package by.du;

import by.du.exception.NotFoundException;
import by.du.model.Meeting;
import by.du.model.Task;
import by.du.repository.Dao;
import by.du.repository.MeetingDao2;
import by.du.repository.TaskDao2;
import by.du.service.MeetingService;
import by.du.service.TaskService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App2 {
    public static void main(String[] args) {
        final MeetingService meetingService = getMeetingService();

        try {
            meetingService.findById(999);
        } catch (NotFoundException ex) {
            log.error("not found meeting[{}]", ex.getId());
        }

        System.out.println(meetingService.findById(2));

        final TaskService taskService = getTaskService();

        try {
            taskService.findById(999);
        } catch (NotFoundException ex) {
            log.error("not found task[{}]", ex.getId());
        }
    }

    private static MeetingService getMeetingService() {
        final Dao<Meeting> meetingDao = new MeetingDao2();
        return new MeetingService(meetingDao);
    }

    private static TaskService getTaskService() {
        final Dao<Task> dao = new TaskDao2();
        return new TaskService(dao);
    }
}
