package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.*;

@Controller
public class LoginController {
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/main.jsp");
		return mv;
	}

	@RequestMapping("/user/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/user/login.jsp");
		
		String req = request.getParameter("request");
		
		if (req != null && "login".equals(req)) {
			// �����¼����
			String name = request.getParameter("name"), passwd = request.getParameter("passwd");
			SysUtil.log("�û���", name);
			SysUtil.log("����", passwd);
			if (name == null || passwd == null) {
				mv.addObject("login_result", "��¼ʧ��, �����������˺ź�����");
				return mv;
			}
		}
		return mv;
	}
}
