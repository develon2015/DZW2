package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	
	@RequestMapping("/search")
	public String search(@RequestParam(value = "q", defaultValue = "³ö×âÎÝ") String q,
			Model model) {
		if ("".equals(q)) {
			q = "È«²¿";
		}
		model.addAttribute("q", q);
		return "/search.jsp";
	}
}
