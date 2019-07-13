package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.DBI;
import common.DateUtil;
import common.SysUtil;
import em.HouseItem;
import em.Order;
import em.User;

@Controller
public class HouseController {
	private static PreparedStatement psmt = null;
	
	private static final String sql = 
//			"SELECT * FROM house WHERE enable=1 AND " +
			"SELECT * FROM house WHERE " +
			"id=?";
	
	@RequestMapping("/house")
	public String search(
			@RequestParam(value = "id", required = true) int id,
			Model model) throws Exception {
		HouseItem house = null;
		try {
			psmt = DBI.getConnection().prepareStatement(sql);
			psmt.clearParameters();
			psmt.setInt(1, id);
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				house = new HouseItem(rs);
			}
		} catch(Exception e) {
			SysUtil.log(e);
			throw e;
		}
		if (house == null) {
			return "redirect:/index.html";
		}
		model.addAttribute("house", house);
		return "/house.jsp";
	}
	
	@RequestMapping("/buy")
	public String buy(
			@CookieValue(name="uid", defaultValue="0") int uid,
			@RequestParam(value = "id", required = true) int hid,
			@RequestParam(value = "d1", required = true) String d1,
			@RequestParam(value = "d2", required = true) String d2,
			Model model) {
		User user = LoginController.getUser(uid);
		if (user == null) {
			return "redirect:/user/login.html";
		}
		
		// 日期初步检测
		if (!d1.matches("(\\d){4}-(\\d){2}-(\\d){2}") || !d2.matches("(\\d){4}-(\\d){2}-(\\d){2}")) {
			model.addAttribute("err", "日期不正确");
			return "forward:/house.html";
		}
		
		try {
			// 日期检测
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date date1 = new java.sql.Date(dateFormat.parse(d1).getTime());
			java.sql.Date date2 = new java.sql.Date(dateFormat.parse(d2).getTime());
			java.sql.Date now = new java.sql.Date(
					dateFormat.parse(new java.sql.Date(System.currentTimeMillis()).toString())
					.getTime());
			
			if (date1.compareTo(date2) >= 0 || date1.compareTo(now) < 0) {
				model.addAttribute("err", "日期不正确, 请重新选择");
				return "forward:/house.html";
			}
			
			HouseItem house = null;
			try {
				house = new HouseItem(hid); // 查询数据库
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
			
			if (house.enable != 1) {
				// 暂未通过审核
				model.addAttribute("err", "该房屋暂不可出租");
				return "forward:/house.html";
			}
			
			int n = DateUtil.daysBetween(date1, date2);
			if (n < house.time_short || n > house.time_long) {
				model.addAttribute("err", "租期超出房东规定");
				return "forward:/house.html";
			}
			
			// 冲突检测
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM orde WHERE hid=? AND status=1");
			psmt.clearParameters();
			psmt.setInt(1, hid);
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();
			List<Order> list = new ArrayList<Order>();
			while (rs.next()) {
				Order o = new Order(rs);
				System.out.printf("%s, %s  %s, %s\n", date1, date2, o.times, o.timee);
				System.out.println((date1.compareTo(o.timee) >= 0));
				System.out.println(date2.compareTo(o.times) <= 0);
				if (!(date1.compareTo(o.timee) >= 0 || date2.compareTo(o.times) <= 0)) {
					list.add(o);
				}
			}
			rs.close();
			psmt.close();

			if (list.size() != 0) {
				// 至少一个冲突订单
				System.out.println(list.size() + "个订单冲突");
				String info = "以下日期已被订购, 请重新选择";
				model.addAttribute("list", list);
				for (int i = 0; i < list.size(); i ++ ) {
					info += "\n" + (i + 1) + ": " + list.get(i).times.toString() + " ~ " + list.get(i).timee.toString();
				}
				SysUtil.log("info", info);
				model.addAttribute("err", info.replace("\n", "<br>"));
				return "forward:/house.html";
			}
			/*
CREATE TABLE IF NOT EXISTS orde(
	id INT AUTO_INCREMENT, 
	hid INT NOT NULL,			#House ID
	uid INT NOT NULL,			#预定者
	times DATE NOT NULL,
	timee DATE NOT NULL,
	n INT NOT NULL,
	price DOUBLE NOT NULL,			#价格
	date DATETIME NOT NULL DEFAULT NOW(),
	status int NOT NULL DEFAULT 0, # 状态 0待处理 1OK 2拒绝
	PRIMARY KEY(id)
)DEFAULT CHARSET=UTF8;
			 */
			System.out.println("订单无冲突, 继续处理");
			psmt = DBI.getConnection().prepareStatement(
					"INSERT INTO orde(hid, uid, times, timee, n, price, date, status) " +
					"VALUE(?, ?, ?, ?, ?, ?, NOW(), 0)");
			psmt.setInt(1, hid);
			psmt.setInt(2, user.getUid());
			psmt.setString(3, date1.toString());
			psmt.setString(4, date2.toString());
			psmt.setInt(5, n);
			psmt.setDouble(6, n * house.price);
			System.out.println(psmt);
			int r = psmt.executeUpdate();
			if (r != 1) {
				model.addAttribute("err", "订购失败, 请稍后再试");
				return "forward:/house.html";
			}
			model.addAttribute("err", "预定成功, 请向商家支付" + n * house.price + "元");
			model.addAttribute("action", String.format("location.href = '%s/user/show.html';", SysUtil.get("path")) );
			return "forward:/house.html";
		} catch(Exception e) {
			SysUtil.log(e);
		}
		return "redirect:/index.html";
	}

	@RequestMapping("/showimg")
	public String showimg(
			@RequestParam(value = "url", required = true) String url,
			Model model) {
		model.addAttribute("url", url);
		return "/showimg.jsp";
	}
}
