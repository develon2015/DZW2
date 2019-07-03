package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.SysUtil;

@Controller
public class LoginController {
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/main.jsp");
		return mv;
	}

	@RequestMapping("/user/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ModelAndView mv = new ModelAndView("/user/login.jsp");
		
		String req = request.getParameter("request");
		
		if (req != null && "register".equals(req)) {
			// ����ע������
			String name = request.getParameter("name"), passwd = request.getParameter("passwd"),
					passwd2 = request.getParameter("passwd2");
			SysUtil.log("ע��, �û���", name);
			SysUtil.log("ע��, ����", passwd);
			SysUtil.log("ע��, ����", passwd2);
			if (passwd == null || passwd2 == null) {
				mv.addObject("register_result", "ע��ʧ��, ����������");
				return mv;
			}
			if (!passwd.equals(passwd2)) {
				mv.addObject("register_result", "ע��ʧ��, ������������벻һ��");
				return mv;
			}
		}
		return mv;
	}
}
