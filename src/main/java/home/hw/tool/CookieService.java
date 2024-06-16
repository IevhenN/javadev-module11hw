package home.hw.tool;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;

public class CookieService {
    public static String LAST_TIME_ZONE = "lastTimezone";
    private Cookie[] cookies;

    public CookieService(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public Cookie getCookie(String nameCookie) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(nameCookie))
                .findFirst()
                .orElse(new Cookie(nameCookie, ""));
    }
}
