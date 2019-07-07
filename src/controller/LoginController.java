package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.HttpTools;
import common.SysUtil;
import em.User;

@Controller
public class LoginController {
	private static final String sql = "SELECT uid FROM user WHERE name=? AND passwd=MD5(MD5(?))";
	private static PreparedStatement psmt = null;
	private static Map<String, User> userList = new HashMap<String, User>();
	
	/**
	 * 返回当前登录用户
	 * @param request
	 * @return
	 * 		null 用户未登录
	 */
	public static User getUser(HttpServletRequest request, HttpServletResponse response) {
		String uid = null;
		Cookie[] cs = request.getCookies();
		if (cs == null)
			return null;
		for (Cookie c : cs) {
			if ("uid".equals(c.getName()) ) {
				uid = c.getValue();
				User user = userList.get(uid);
				if (user == null) {
					SysUtil.log("登录已过期", c.getValue());
					Cookie cookie = new Cookie("uid", uid);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
				return user;
			}
		}
		return null;
	}

	/**
	 * 返回当前登录用户
	 * @param uid
	 * @return
	 * 		null 用户未登录
	 */
	public static User getUser(int uid) {
		return userList.get("" + uid);
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/main.jsp");
		User user = getUser(request, response);
		if (user != null) {
				SysUtil.log("已登录用户访问主页", user);
				mv.addObject("user", user);
				return mv;
		}
		return mv;
	}
	
	private boolean login(String name, String passwd, HttpServletResponse response) {
		try {
			if (psmt == null || !DBI.isValid()) {
				psmt = DBI.getConnection().prepareStatement(sql);
			}
			psmt.setString(1, name);
			psmt.setString(2, passwd + name);
			SysUtil.log(name + ", " + passwd, psmt);
			ResultSet rs = psmt.executeQuery();
			if (!rs.next()) {
				SysUtil.log("用户" + name + "登录失败");
				return false;
			}
			int uid = rs.getInt(1);
			SysUtil.log("用户" + name + "登录成功, id为" + uid);
			Cookie cookie = new Cookie("uid", uid + "");
			cookie.setPath("/");
			response.addCookie(cookie);
			User user = new User(uid, name);
			userList.put("" + uid, user);
			return true;
		} catch (Exception e) {
			SysUtil.log(e);
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping("/user/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/user/login.jsp");
		
		String req = request.getParameter("request");
		
		if (req != null && "login".equals(req)) {
			// 处理登录请求
			String name = request.getParameter("name"), passwd = request.getParameter("passwd");
			try {
				name = name.trim();
				passwd = passwd.trim();
			} catch(Exception e) {}
			
			if (name == null || passwd == null || "".equals(name) || "".equals(passwd)) {
				mv.addObject("login_result", "登录失败, 请重新输入账号和密码");
				return mv;
			}
			
			// 查询数据库
			try {
				if (login(name, passwd, response)) {
					// 登录成功
					mv.setViewName("redirect:/index.html");
					return mv;
				}
			} catch(Exception e) {
				SysUtil.log(e);
			}
			mv.addObject("login_result", "登录失败, 请重试");
			return mv;
		}
		return mv;
	}
	
	@RequestMapping("/user/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("redirect:/index.html");
		String uid = HttpTools.getCookie(request, "uid");
		if (uid != null) {
			userList.remove(uid);
		}
		return mv;
	}

}
