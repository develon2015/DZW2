package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.SysUtil;
import em.HouseItem;
import em.User;

@Controller
public class AdminController {
	
	@RequestMapping("/mgr")
	public String admin(
			@RequestParam(name = "id") int id,
			@RequestParam(name = "r") int r,
			@CookieValue(name = "uid", required = false, defaultValue = "0") int uid,
			Model model) {
		User admin = LoginController.getUser(uid);
		if (admin == null) {
			model.addAttribute("info", "����δ��¼, ǰ����¼");
			model.addAttribute("action", String.format("location.href = '%s/user/login.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		if (!SysUtil.get("admin").equals(admin.getName()) ) {
			model.addAttribute("info", "������ϵͳ����Ա");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		HouseItem h = new HouseItem(id);
		boolean n = h.setStatus(r);
		model.addAttribute("info", n ? "�����ɹ�" : "����ʧ��");
		model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
		return "/alert.jsp";
	}
}
