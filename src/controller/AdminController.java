package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.DBI;
import common.SysUtil;
import em.HouseItem;
import em.Order;
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
		
		if (r == 3 && h.enable == 0) {
			boolean n = h.setStatus(r);
			model.addAttribute("info", n ? "取消成功, 您可以重新申请审核" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		model.addAttribute("info", "不允许的操作");
		model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
		return "/alert.jsp";
	}
	
	@RequestMapping("/omgr")
	public String omgr(
			@CookieValue(name = "uid") int uid,
			@RequestParam(name = "id") int id,
			@RequestParam(name = "r") int r,
			Model model) {
		User master = LoginController.getUser(uid);
		if (master == null) {
			model.addAttribute("info", "您尚未登录, 前往登录");
			model.addAttribute("action", String.format("location.href = '%s/user/login.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		Order o = new Order(id);
		HouseItem h = new HouseItem(o.hid);
		System.out.printf("%d, %d, %d, %d\n", h.uid, master.getUid(), r, o.status);
		
		// guest
		if (o.uid == master.getUid() && r == 2 && (o.status == 0 || o.status == 1)) {
			boolean n = o.setStatus(r);
			model.addAttribute("info", n ? "已取消预定" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}
		
		if (h.uid == master.getUid() && r == 1 && o.status == 0) {
			// 冲突检测
			try {
				java.sql.Date date1 = o.times;
				java.sql.Date date2 = o.timee;
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				java.sql.Date date1 = new java.sql.Date(dateFormat.parse(o.times.toString()).getTime());
//				java.sql.Date date2 = new java.sql.Date(dateFormat.parse(o.timee.toString()).getTime());
				
				PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM orde WHERE hid=? AND status=1");
				psmt.clearParameters();
				psmt.setInt(1, o.hid);
				System.out.println(psmt);
				ResultSet rs = psmt.executeQuery();
				List<Order> list = new ArrayList<Order>();
				while (rs.next()) {
					Order o2 = new Order(rs);
					System.out.printf("%s, %s  %s, %s\n", date1, date2, o2.times, o2.timee);
					System.out.println((date1.compareTo(o2.timee) >= 0));
					System.out.println(date2.compareTo(o2.times) <= 0);
					if (!(date1.compareTo(o2.timee) >= 0 || date2.compareTo(o2.times) <= 0)) {
						list.add(o2);
					}
				}
				rs.close();
				psmt.close();

				if (list.size() != 0) {
					// 至少一个冲突订单
					System.out.println(list.size() + "个订单冲突");
					String info = "订单冲突, 以下日期已被订购";
					model.addAttribute("list", list);
					for (int i = 0; i < list.size(); i ++ ) {
						info += "\n" + (i + 1) + ": " + list.get(i).times.toString() + " ~ " + list.get(i).timee.toString();
					}
					SysUtil.log("info", info);
					model.addAttribute("info", info.replace("\n", "<br>"));
					model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
					return "/alert.jsp";
				}
			} catch(Exception e) {
				SysUtil.log(e);
				model.addAttribute("info", e.getMessage());
				model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
				return "/alert.jsp";
			}
			
			boolean n = o.setStatus(r);
			model.addAttribute("info", n ? "已同意订单" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}

		if (h.uid == master.getUid() && r == 2 && o.status == 0) {
			boolean n = o.setStatus(r);
			model.addAttribute("info", n ? "已拒绝订单" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}

		if (h.uid == master.getUid() && r == 2 && o.status == 1) {
			boolean n = o.setStatus(r);
			model.addAttribute("info", n ? "已取消订单" : "操作失败");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
			return "/alert.jsp";
		}

		model.addAttribute("info", "不允许的操作");
		model.addAttribute("action", String.format("location.href = '%s/user/show.html'", SysUtil.get("path")) );
		return "/alert.jsp";
	}
}
