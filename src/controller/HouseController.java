package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.DBI;
import common.SysUtil;
import em.HouseItem;

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
			@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "d1", required = true) String d1,
			@RequestParam(value = "d2", required = true) String d2,
			Model model) {
		model.addAttribute("house", new HouseItem(id));
		return "/house.jsp";
	}

	@RequestMapping("/showimg")
	public String showimg(
			@RequestParam(value = "url", required = true) String url,
			Model model) {
		model.addAttribute("url", url);
		return "/showimg.jsp";
	}
}
