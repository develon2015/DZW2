package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		
		
		if (LoginController.getUser(request, response) == null) {
			mv.setViewName("redirect:/user/login.html");
			return mv;
		}
		
		mv.setViewName("/user/show_self.jsp");
		mv.addObject("user", LoginController.getUser(request, response));
		return mv;
	}
	
	@RequestMapping("/user/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
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

			if (!phone.matches("^1(\\d){10}$")) {
				mv.addObject("info", "修改失败, 手机号不正确");
				return mv;
			}

			user.setName(name);
			user.setPhone(phone);
			user.setEmail(email);
			// 更新数据库
			try {
				if (user.updateDB()) {
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
}
