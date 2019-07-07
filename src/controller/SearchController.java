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
	private static final String ALL = "全部";
	private static PreparedStatement psmt = null;
	private static PreparedStatement psmtn = null;
	
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?) LIMIT ?, ?";
	
	private static final String sqln = // 查询结果数
			"SELECT COUNT(*) FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?)";
	
	private static final int MAX = 8; // 每页最大数量
	
	private List<HouseItem> list(String query) {
		List<HouseItem> list = new ArrayList<HouseItem>();
		String[] qs = query.split(" ");
		for (String q : qs) {
			try {
				// 首先查询数量, 计算页数传入pn
				if (psmtn == null || psmtn.isClosed()) {
					psmtn = DBI.getConnection().prepareStatement(sqln);
				}
				psmtn.clearParameters();
				psmtn.setString(1, String.format("%%%s%%", q));
				psmtn.setString(2, String.format("%%%s%%", q));
				ResultSet rsn = psmtn.executeQuery();
	
				
				// limit查询
				if (psmt == null || psmt.isClosed()) {
					psmt = DBI.getConnection().prepareStatement(sql);
				}
				psmt.clearParameters();
				psmt.setString(1, String.format("%%%s%%", q));
				psmt.setString(2, String.format("%%%s%%", q));
	//			psmt.setInt(3, (page - 1) * MAX);
				psmt.setInt(4, MAX);
				SysUtil.log("搜索", psmt);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					list.add(new HouseItem(rs));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@RequestMapping("/search")
	public String search(
			@RequestParam(value = "q", defaultValue = ALL) String q,
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) throws Exception {
		int n = 0; // 搜索结果总数
		int pn = 0; // 最大页数
		
		if ("".equals(q)) {
			q = ALL;
		}
		model.addAttribute("q", q);
		if (ALL.equals(q)) {
			q = "%";
		}
		
		List<HouseItem> list = list(q);
		
		n = list.size();
		if (n == 0) {
			model.addAttribute("nothing", "抱歉, 啥也没找到");
		}
		pn = n / MAX + 1; // 最大页数
		model.addAttribute("pn", pn);
		
		SysUtil.log("搜索结果", list.size());
		
		for (HouseItem s : list) {
			//SysUtil.log("list", s);
		}
		
		model.addAttribute("list", list);
		
		return "/search.jsp";
	}
}
