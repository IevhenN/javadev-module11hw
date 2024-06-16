package home.hw.servlet.controller;

import home.hw.tool.Time;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timeZone = req.getParameter("timezone");
        if (timeZone != null) {
            try {
                ZoneId.of(Time.convertTimeZone(timeZone));
            } catch (DateTimeException e) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.setContentType("text/html; charset=UTF-8");
                res.getWriter().write("<html><body>Invalid timezone</body></html>");
                return;
            }

        }

        chain.doFilter(req, res);
    }
}
