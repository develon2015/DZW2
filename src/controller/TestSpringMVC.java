package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestSpringMVC {
	
	@RequestMapping("/ok")
	public String ok(
			@CookieValue(name = "uid", defaultValue = "0") int uid,
			Model model) {
		System.out.println(uid);
		return "/test.jsp";
	}
	
    @RequestMapping("/test")
	public String helloWorld(Model model) {
    	model.addAttribute("info", "Hello\\nHello");
    	return "forward:/ok.html";
    }
}