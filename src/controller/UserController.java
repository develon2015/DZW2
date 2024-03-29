package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.SysUtil;
import em.HouseItem;
import em.Order;
import em.User;

@Controller
public class UserController {
	
	@RequestMapping("/user/show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		
		String userName = request.getParameter("user_name");
		if (userName != null && !"".equals(userName.trim()) ) {
			// 查询其他用户
			mv.setViewName("/user/show_user.jsp");
			mv.addObject("user", userName);
			return mv;
		}
		
		User user = LoginController.getUser(request, response);
		
		if (user == null) {
			mv.setViewName("redirect:/user/login.html");
			return mv;
		}
		
		if (SysUtil.get("admin").equals(user.getName()) ) { // 系统管理员入口
			mv.setViewName("/admin.jsp");
			List<HouseItem> ls = listHouse(request, response);
			mv.addObject("list", ls);
			return mv;
		}
		
		mv.setViewName("/user/show_self.jsp");

		List<HouseItem> list = listLease(request, response);
		mv.addObject("listlease", list);
		mv.addObject("user", LoginController.getUser(request, response));
		
		List<Order> list2 = listorder(request, response);
		mv.addObject("listorder", list2);
		
		return mv;
	}
	
	/** 管理员接口 */
	private List<HouseItem> listHouse(HttpServletRequest request, HttpServletResponse response) {
		List<HouseItem> ls = new ArrayList<HouseItem>();
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM house GROUP BY id DESC");
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				ls.add(new HouseItem(rs));
			}
			rs.close();
			psmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	private List<Order> listorder(HttpServletRequest request, HttpServletResponse response) {
		List<Order> list = new ArrayList<Order>();
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM orde WHERE uid=? GROUP BY id DESC");
			psmt.setInt(1, LoginController.getUser(request, response).getUid());
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new Order(rs));
			}
			rs.close();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private List<HouseItem> listLease(HttpServletRequest request, HttpServletResponse response) {
		List<HouseItem> list = new ArrayList<HouseItem>();
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM house WHERE uid_master=? GROUP BY id DESC");
			psmt.setInt(1, LoginController.getUser(request, response).getUid());
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new HouseItem(rs));
			}
			rs.close();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/user/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/user/update.jsp");
		User user = null;
		User user2 = null; // 数据库原始对象
		if ((user2 = LoginController.getUser(request, response)) == null) {
			mv.setViewName("redirect:/user/login.html");
			return mv;
		}
		String req = request.getParameter("request");
		
		try {
			mv.addObject("user", user = user2.clone());
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}

		if (req != null && "update".equals(req)) {
			// 处理修改请求
			String name = request.getParameter("name"), phone = request.getParameter("phone"),
					email = request.getParameter("email");
			try {
				name = name.trim();
				phone = phone.trim();
				email = email.trim();
			} catch(Exception e) {}

			if (name == null || phone == null
					 || "".equals(name) || "".equals(phone)) {
				mv.addObject("info", "修改失败, 请检查输入");
				return mv;
			}

			if (!phone.matches("^1[3578](\\d){9}$")) {
				mv.addObject("info", "修改失败, 手机号不正确");
				return mv;
			}

			user.setName(name);
			user.setPhone(phone);
			user.setEmail(email);
			// 更新数据库
			try {
				if (user.commit()) {
					// 成功
					user2.update();
					mv.setViewName("redirect:/user/show.html");
					return mv;
				} else {
					mv.addObject("info", "修改失败, 请重新尝试");
				}
			} catch (Exception e) {
				mv.addObject("info", "修改失败, " + e.getMessage());
			}
		}

		mv.setViewName("/user/update.jsp");
		return mv;
	}

	@RequestMapping("/user/uppswd")
	public ModelAndView uppaswd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/user/uppswd.jsp");
		User user = null;
		if ((user = LoginController.getUser(request, response)) == null) {
			mv.setViewName("redirect:/user/login.html");
			return mv;
		}
		String req = request.getParameter("request");
		
		if (req != null && "update".equals(req)) {
			// 处理修改请求
			String po = request.getParameter("po"), p1 = request.getParameter("p1"),
					p2 = request.getParameter("p2");
			try {
				po = po.trim();
				p1 = p1.trim();
				p2 = p2.trim();
			} catch(Exception e) {}
			
			if (po == null || p1 == null || p2 == null
					|| "".equals(po) || "".equals(p1) || "".equals(p2)) {
				mv.addObject("info", "修改失败, 请检查输入");
				return mv;
			}
			
			if (!p1.equals(p2)) {
				mv.addObject("info", "两次密码不一致");
				return mv;
			}
			
			// 更新数据库
			try {
				PreparedStatement psmt = DBI.getConnection().prepareStatement(
						"UPDATE user SET passwd=MD5(MD5(?)) WHERE uid=? AND passwd=MD5(MD5(?))");
				psmt.setString(1, p1);
				psmt.setInt(2, user.getUid());
				psmt.setString(3, po);
				System.out.println(psmt);
				int r = psmt.executeUpdate();
				if (r == 1) {
					// 成功
					mv.addObject("info", "修改成功");
					mv.addObject("action", "location.href = 'show.html';");
					return mv;
				} else {
					mv.addObject("info", "修改失败, 请重新尝试");
				}
			} catch (Exception e) {
				mv.addObject("info", "修改失败, " + e.getMessage());
			}
		}
		
		return mv;
	}
}
