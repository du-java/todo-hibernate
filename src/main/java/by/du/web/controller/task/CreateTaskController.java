package by.du.web.controller.task;

import by.du.model.Task;
import by.du.service.TaskService;
import by.du.web.AppContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/task/create")
public class CreateTaskController extends AbstractTaskController {

    @Override
    public void init() throws ServletException {
        taskService = ((AppContext) getServletContext().getAttribute("ctx")).get(TaskService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("tasks/task_create", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String date = req.getParameter("date");
        final String desc = req.getParameter("desc");
        final String isDone = req.getParameter("isDone");

        final Task task = Task.builder()
                .date(LocalDate.parse(date))
                .desc(desc)
                .isDone(Boolean.parseBoolean(isDone))
                .build();

        taskService.create(task);
        resp.sendRedirect("/todo/tasks");
    }
}
