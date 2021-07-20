package by.du.web.controller.task;

import by.du.model.Task;
import by.du.service.TaskService;
import by.du.web.AppContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TasksController extends AbstractTaskController {
    @Override
    public void init() throws ServletException {
        taskService = ((AppContext) getServletContext().getAttribute("ctx")).get(TaskService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final List<Task> tasks = taskService.findAll();
        req.setAttribute("tasks", tasks);
        forward("tasks/tasks", req, resp);
    }
}
