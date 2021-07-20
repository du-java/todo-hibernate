package by.du.web.listener;

import by.du.repository.MeetingDao3;
import by.du.repository.TaskDao2;
import by.du.service.MeetingService;
import by.du.service.TaskService;
import by.du.util.DbConn;
import by.du.util.HibernateConfig;
import by.du.web.AppContext;
import org.hibernate.Session;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DbConn.INSTANCE.getConnection();
//        final Session session = HibernateConfig.createSession();

        final AppContext appCtx = new AppContext();
        appCtx.put(TaskService.class, new TaskService(new TaskDao2()));
//        appCtx.put(MeetingService.class, new MeetingService(new MeetingDao3(session)));

        sce.getServletContext().setAttribute("ctx", appCtx);
    }
}
