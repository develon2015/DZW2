package controller;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.SysUtil;

@Controller
public class RegisterController {

	private boolean register(String name, String passwd, String phone, String email) {
		/*
+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| uid    | int(11)      | NO   | PRI | NULL    | auto_increment |
| name   | varchar(255) | NO   | UNI | NULL    |                |
| passwd | varchar(255) | NO   |     | NULL    |                |
| phone  | varchar(255) | NO   |     | NULL    |                |
| email  | varchar(255) | YES  |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+
		 */
		String sql = String.format("INSERT INTO user(name, passwd, phone, email) VALUE(?, MD5(MD5(?)), ?, %s)", 
				(email == null || "".equals(email)) ? "NULL" : "?");
		SysUtil.log("sql", sql);
		PreparedStatement psmt = null;
		try {
			//
			psmt = DBI.getConnection().prepareStatement(sql);
			psmt.setString(1, name);
			psmt.setString(2, passwd + name + new Date().toString()); // ÃÜÂë¼ÓÃÜ
			psmt.setString(3, phone);
			if (email != null && !"".equals(email)) {
				psmt.setString(4, email);
			}
			SysUtil.log("×¢²á", psmt.toString());
			psmt.execute();
		} catch (Exception e) {
			SysUtil.log(e);
			throw new RuntimeException("Î´Öª´íÎó");
		} finally {
			try { psmt.close(); } catch(Exception e) {}
		}
		return true;
	}

	@RequestMapping("/user/register")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ModelAndView mv = new ModelAndView("/user/register.jsp");

		String req = request.getParameter("request");

		if (req != null && "register".equals(req)) {
			// ´¦Àí×¢²áÇëÇó
			String name = request.getParameter("name"), passwd = request.getParameter("passwd"),
					passwd2 = request.getParameter("passwd2"), phone = request.getParameter("phone"),
					email = request.getParameter("email");

			if (name == null || passwd == null || passwd2 == null || phone == null
					 || "".equals(name) || "".equals(passwd) || "".equals(passwd2) || "".equals(phone)) {
				mv.addObject("register_result", "×¢²áÊ§°Ü, Çë¼ì²éÊäÈë");
				return mv;
			}

			if (!passwd.equals(passwd2)) {
				mv.addObject("register_result", "×¢²áÊ§°Ü, Á½´ÎÊäÈëµÄÃÜÂë²»Ò»ÖÂ");
				return mv;
			}
			
			if (!phone.matches("^(\\d){11}$")) {
				mv.addObject("register_result", "×¢²áÊ§°Ü, ÊÖ»úºÅ²»ÕýÈ·");
				return mv;
			}

			// ×¢²áÊý¾Ý¿â
			try {
				if (register(name, passwd, phone, email)) {
					// ×¢²á³É¹¦
					mv.setViewName("/user/register_succeed.jsp");
					return mv;
				} else {
					mv.addObject("register_result", "×¢²áÊ§°Ü, ÇëÖØÐÂ³¢ÊÔ");
				}
			} catch (Exception e) {
				mv.addObject("register_result", "×¢²áÊ§°Ü, " + e.getMessage());
			}
		}
		return mv;
	}
}
