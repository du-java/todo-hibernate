package by.du.web.controller.task;

import by.du.model.Task;
import by.du.repository.TaskDao2;
import by.du.service.TaskService;
import by.du.web.AppContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/task/update")
public class UpdateTaskController extends AbstractTaskController {

    @Override
    public void init() throws ServletException {
        taskService = ((AppContext) getServletContext().getAttribute("ctx")).get(TaskService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("task", taskService.findById(Integer.parseInt(req.getParameter("id"))));
        forward("tasks/task_update", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Task task = Task.builder()
                .id(Integer.parseInt(req.getParameter("id")))
                .date(LocalDate.parse(req.getParameter("date")))
                .desc(req.getParameter("desc"))
                .isDone(req.getParameter("isDone") != null)
                .build();

        taskService.update(task);
        resp.sendRedirect("/todo/tasks");
    }
}
