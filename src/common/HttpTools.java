package common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpTools {
	
	// ≤È—Øcookie÷µ
	public static String getCookie(HttpServletRequest request, String key) {
		if (key == null || "".equals(key))
			return null;
		Cookie[] cs = request.getCookies();
		for (Cookie c : cs) {
			if (key.equals(c.getName()) ) {
				return c.getValue();
			}
		}
		return null;
	}
}
