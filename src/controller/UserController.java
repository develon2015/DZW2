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
		
		List<HouseItem> list = listLease(request, response);
		mv.setViewName("/user/show_self.jsp");
		mv.addObject("listlease", list);
		mv.addObject("user", LoginController.getUser(request, response));
		return mv;
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
				if (user.updateDB()) {
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
}
