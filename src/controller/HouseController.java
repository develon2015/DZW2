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
}