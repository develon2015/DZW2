package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.DBI;
import common.DateUtil;
import common.SysUtil;
import em.HouseItem;
import em.User;

@Controller
public class HouseController {
	private static PreparedStatement psmt = null;
	
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
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
			return "redirect:/house.html?id=" + hid;
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
				return "redirect:/house.html?id=" + hid;
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
				return "redirect:/house.html?id=" + hid;
			}
			
			int n = DateUtil.daysBetween(date1, date2);
			if (n < house.time_short || n > house.time_long) {
				model.addAttribute("err", "租期超出房东规定");
				return "redirect:/house.html?id=" + hid;
			}
			
/*
CREATE TABLE IF NOT EXISTS order(
	id INT AUTO_INCREMENT, 
	hid INT NOT NULL,			#House ID
	uid INT NOT NULL,			#预定者
	times DATETIIME NOT NULL,
	timee DATETIIME NOT NULL,
	n INT NOT NULL,
	price DOUBLE NOT NULL,			#价格
	date DATETIIME DEFAULT NOW(),
	PRIMARY KEY(id)
)DEFAULT CHARSET=UTF8;
 */
			
			psmt = DBI.getConnection().prepareStatement("");
			psmt.clearParameters();
			psmt.setInt(1, hid);
			ResultSet rs = psmt.executeQuery();
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
