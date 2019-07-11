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
import em.HouseItem;
import em.User;

@Controller
public class UserController {
	
	@RequestMapping("/user/show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		
		String userName = request.getParameter("user_name");
		if (userName != null && !"".equals(userName.trim()) ) {
			// ��ѯ�����û�
			mv.setViewName("/user/show_user.jsp");
			mv.addObject("user", userName);
			return mv;
		}
		
		
		if (LoginController.getUser(request, response) == null) {
			mv.setViewName("redirect:/user/login.html");
			return mv;
		}
		
		mv.setViewName("/user/show_self.jsp");

		List<HouseItem> list = listLease(request, response);
		mv.addObject("listlease", list);
		mv.addObject("user", LoginController.getUser(request, response));
		
		List<HouseItem> list2 = listorder(request, response);
		mv.addObject("listorder", list2);
		
		return mv;
	}
	
	private List<HouseItem> listorder(HttpServletRequest request, HttpServletResponse response) {
		List<HouseItem> list = new ArrayList<HouseItem>();
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM order WHERE uid_master=?");
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

	private List<HouseItem> listLease(HttpServletRequest request, HttpServletResponse response) {
		List<HouseItem> list = new ArrayList<HouseItem>();
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM house WHERE uid_master=?");
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
		User user2 = null; // ���ݿ�ԭʼ����
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
			// �����޸�����
			String name = request.getParameter("name"), phone = request.getParameter("phone"),
					email = request.getParameter("email");
			try {
				name = name.trim();
				phone = phone.trim();
				email = email.trim();
			} catch(Exception e) {}

			if (name == null || phone == null
					 || "".equals(name) || "".equals(phone)) {
				mv.addObject("info", "�޸�ʧ��, ��������");
				return mv;
			}

			if (!phone.matches("^1[3578](\\d){9}$")) {
				mv.addObject("info", "�޸�ʧ��, �ֻ��Ų���ȷ");
				return mv;
			}

			user.setName(name);
			user.setPhone(phone);
			user.setEmail(email);
			// �������ݿ�
			try {
				if (user.commit()) {
					// �ɹ�
					user2.update();
					mv.setViewName("redirect:/user/show.html");
					return mv;
				} else {
					mv.addObject("info", "�޸�ʧ��, �����³���");
				}
			} catch (Exception e) {
				mv.addObject("info", "�޸�ʧ��, " + e.getMessage());
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
			// �����޸�����
			String po = request.getParameter("po"), p1 = request.getParameter("p1"),
					p2 = request.getParameter("p2");
			try {
				po = po.trim();
				p1 = p1.trim();
				p2 = p2.trim();
			} catch(Exception e) {}
			
			if (po == null || p1 == null || p2 == null
					|| "".equals(po) || "".equals(p1) || "".equals(p2)) {
				mv.addObject("info", "�޸�ʧ��, ��������");
				return mv;
			}
			
			if (!p1.equals(p2)) {
				mv.addObject("info", "�������벻һ��");
				return mv;
			}
			
			// �������ݿ�
			try {
				PreparedStatement psmt = DBI.getConnection().prepareStatement(
						"UPDATE user SET passwd=MD5(MD5(?)) WHERE uid=? AND passwd=MD5(MD5(?))");
				psmt.setString(1, p1);
				psmt.setInt(2, user.getUid());
				psmt.setString(3, po);
				System.out.println(psmt);
				int r = psmt.executeUpdate();
				if (r == 1) {
					// �ɹ�
					mv.addObject("info", "�޸ĳɹ�");
					mv.addObject("action", "location.href = 'show.html';");
					return mv;
				} else {
					mv.addObject("info", "�޸�ʧ��, �����³���");
				}
			} catch (Exception e) {
				mv.addObject("info", "�޸�ʧ��, " + e.getMessage());
			}
		}
		
		return mv;
	}
}
