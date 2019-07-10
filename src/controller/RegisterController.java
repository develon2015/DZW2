package controller;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.SysUtil;

@Controller
public class RegisterController {
	private static final String sqlA = "INSERT INTO user(name, passwd, phone, email) VALUE(?, MD5(MD5(?)), ?, NULL)";
	private static final String sqlB = "INSERT INTO user(name, passwd, phone, email) VALUE(?, MD5(MD5(?)), ?, ?)";
	private static PreparedStatement psmtA = null;
	private static PreparedStatement psmtB = null;
	
	private PreparedStatement getPsmt(String email) {
		try {
		if (email == null || "".equals(email)) {
			// A
			if (psmtA == null || !DBI.isValid()) {
				psmtA = DBI.getConnection().prepareStatement(sqlA);
			}
			return psmtA;
		} else {
			// B
			if (psmtB == null || !DBI.isValid()) {
				psmtB = DBI.getConnection().prepareStatement(sqlB);
			}
			return psmtB;
		}
		} catch(Exception e) {
			SysUtil.log(e);
		}
		return null;
	}

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
		PreparedStatement psmt = getPsmt(email);
		try {
			//
			psmt.setString(1, name);
			psmt.setString(2, passwd); // 密码加密
			psmt.setString(3, phone);
			if (email != null && !"".equals(email)) {
				psmt.setString(4, email);
			}
			SysUtil.log("注册", psmt.toString());
			int i = psmt.executeUpdate();
			if (i != 1)
				throw new RuntimeException("请稍后再试");
		} catch (Exception e) {
			SysUtil.log(e);
			throw new RuntimeException(e.getMessage()
					.matches("Duplicate entry .+ for key 'name'") ? "用户名已被注册" : e.getMessage());
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
			// 处理注册请求
			String name = request.getParameter("name"), passwd = request.getParameter("passwd"),
					passwd2 = request.getParameter("passwd2"), phone = request.getParameter("phone"),
					email = request.getParameter("email");
			try {
				name = name.trim();
				passwd = passwd.trim();
				passwd2 = passwd2.trim();
				phone = phone.trim();
				email = email.trim();
			} catch(Exception e) {}

			if (name == null || passwd == null || passwd2 == null || phone == null
					 || "".equals(name) || "".equals(passwd) || "".equals(passwd2) || "".equals(phone)) {
				mv.addObject("register_result", "注册失败, 请检查输入");
				return mv;
			}

			if (!passwd.equals(passwd2)) {
				mv.addObject("register_result", "注册失败, 两次输入的密码不一致");
				return mv;
			}
			
			if (!phone.matches("^1[3578](\\d){9}$")) {
				mv.addObject("register_result", "注册失败, 手机号不正确");
				return mv;
			}

			// 注册数据库
			try {
				if (register(name, passwd, phone, email)) {
					// 注册成功
					mv.setViewName("/user/register_succeed.jsp");
					return mv;
				} else {
					mv.addObject("register_result", "注册失败, 请重新尝试");
				}
			} catch (Exception e) {
				mv.addObject("register_result", "注册失败, " + e.getMessage());
			}
		}
		return mv;
	}
}
