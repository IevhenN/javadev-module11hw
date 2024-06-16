package home.hw.servlet.controller;

import home.hw.tool.CookieService;
import home.hw.tool.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;


@WebServlet(value = {"/", "/time"})
public class TimeServlet extends HttpServlet {
    public static final String PARAM_TIMEZONE = "timezone";
    public static final String VAR_CURRENT_TIME = "currentTime";
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Context context = new Context();

        CookieService cookieService = new CookieService(req.getCookies());
        Cookie currentCookie = cookieService.getCookie(CookieService.LAST_TIME_ZONE);
        String timeZoneStr = Time.getTimeZone(req.getParameter(PARAM_TIMEZONE), currentCookie);
        timeZoneStr = Time.convertTimeZone(timeZoneStr);

        context.setVariable(VAR_CURRENT_TIME, Time.getCurrentTime(timeZoneStr));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        Cookie newCookie = new Cookie(CookieService.LAST_TIME_ZONE, timeZoneStr);
        if (!newCookie.equals(currentCookie)) {
            resp.addCookie(newCookie);
        }

        templateEngine.process("time", context, resp.getWriter());
    }
}
