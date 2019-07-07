package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.DBI;
import common.SysUtil;
import em.HouseItem;

@Controller
public class SearchController {
	private static final String ALL = "È«²¿";
	private static PreparedStatement psmt = null;
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
			"name LIKE ?";
	
	@RequestMapping("/search")
	public String search(@RequestParam(value = "q", defaultValue = ALL) String q,
			Model model) throws Exception {
		if ("".equals(q)) {
			q = ALL;
		}
		model.addAttribute("q", q);
		
		if (psmt == null || psmt.isClosed()) {
			psmt = DBI.getConnection().prepareStatement(sql);
		}
		psmt.clearParameters();
		psmt.setString(1, q);
		SysUtil.log("ËÑË÷", psmt);
		ResultSet rs = psmt.executeQuery();
		List<HouseItem> list = new ArrayList<HouseItem>();
		while (rs.next()) {
			list.add(new HouseItem(rs));
		}
		
		for (HouseItem s : list) {
			SysUtil.log("list", s);
		}
		
		model.addAttribute("list", list);
		
		return "/search.jsp";
	}
}
