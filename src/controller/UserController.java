package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
