package by.du.web.controller.task;

import by.du.model.Task;
import by.du.service.TaskService;
import by.du.web.AppContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/task/delete")
public class DeleteTaskController extends AbstractTaskController {

    @Override
    public void init() throws ServletException {
        taskService = ((AppContext) getServletContext().getAttribute("ctx")).get(TaskService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("task", taskService.findById(Integer.parseInt(req.getParameter("id"))));
        forward("tasks/task_delete", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        taskService.delete(Task.builder().id(Integer.parseInt(req.getParameter("id"))).build());
        resp.sendRedirect("/todo/tasks");
    }
}
