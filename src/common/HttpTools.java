package common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpTools {
	
	/** 
	 * ��ѯcookieֵ
	 * @param request
	 * @param key
	 * @return
	 * 	null û�и�cookieֵ
	 */
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
