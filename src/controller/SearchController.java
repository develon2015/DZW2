package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	
	@RequestMapping("/search")
	public String search(@RequestParam(value = "q", defaultValue = "������") String q,
			Model model) {
		if ("".equals(q)) {
			q = "ȫ��";
		}
		model.addAttribute("q", q);
		return "/search.jsp";
	}
}
