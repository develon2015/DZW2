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
			model.addAttribute("info", "您尚未登录, 前往登录");
			model.addAttribute("action", String.format("location.href = '%s/user/login.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		if (!SysUtil.get("admin").equals(admin.getName()) ) {
			model.addAttribute("info", "您不是系统管理员");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		HouseItem h = new HouseItem(id);
		boolean n = h.setStatus(r);
		model.addAttribute("info", n ? "操作成功" : "操作失败");
		model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
		return "/alert.jsp";
	}
	
	@RequestMapping("/mmgr")
	public String mmgr(
			@RequestParam(name = "id") int id,
			@RequestParam(name = "r") int r,
			@CookieValue(name = "uid", required = false, defaultValue = "0") int uid,
			Model model) {
		User user = LoginController.getUser(uid);
		if (user == null) {
			model.addAttribute("info", "您尚未登录, 前往登录");
			model.addAttribute("action", String.format("location.href = '%s/user/login.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}

		HouseItem h = new HouseItem(id);
		
		if (h.uid != user.getUid()) {
			model.addAttribute("info", "您没有权限");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		// 0审核中 1通过 2拒绝 3商家下架 4管理员下架
		if (r == 0 && h.enable == 3) {
			boolean n = h.setStatus(r);
			model.addAttribute("info", n ? "操作成功, 等待管理员审核" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}

		if (r == 3 && h.enable == 1) {
			boolean n = h.setStatus(r);
			model.addAttribute("info", n ? "下架成功, 您可以重新审核上架" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		model.addAttribute("info", "不允许的操作");
		model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
		return "/alert.jsp";
	}
}
