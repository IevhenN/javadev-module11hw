package home.hw.tool;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public final class Time {
    public static final String UTC = "UTC";
    public static final String GMT = "GMT";

    private Time() {

    }

    public static String getCurrentTime(String timeZoneStr) {
        ZoneId zoneId = ZoneId.of(timeZoneStr.replace(UTC, GMT));
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
        String formattedDateTime = now.format(formatter);

        String offset = timeZoneStr.replace(UTC, "");
        return formattedDateTime.replace(UTC, UTC + offset);
    }

    public static String convertTimeZone(String timeZoneStr) {
        return timeZoneStr.replace(" ", "+");
    }

    public static String getTimeZone(String timeZoneStr, Cookie cookie) {
        if (timeZoneStr == null) {
            timeZoneStr = cookie.getValue();
            timeZoneStr = timeZoneStr.isEmpty() ? Time.UTC : timeZoneStr;
        }
        return timeZoneStr;
    }
}
