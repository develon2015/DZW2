package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

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
//	private static PreparedStatement psmtn = null;
	
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?)";
	
//	private static final String sqln = // 查询结果数
//			"SELECT COUNT(*) FROM house WHERE enable=FALSE AND " +
//			"(name LIKE ? OR address LIKE ?)";
	
	private static final int MAX = 8; // 每页最大数量
	
	private Map<Integer, HouseItem> list(String query) {
		Map<Integer, HouseItem> list = new HashMap<Integer, HouseItem>();
		String[] qs = query.split(" ");
		if (qs.length < 1)
			SysUtil.log("啥也不干");
		for (String q : qs) {
			try {
				if (psmt == null || psmt.isClosed()) {
					psmt = DBI.getConnection().prepareStatement(sql);
				}
				psmt.clearParameters();
				psmt.setString(1, String.format("%%%s%%", q));
				psmt.setString(2, String.format("%%%s%%", q));
				SysUtil.log("搜索", psmt);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id");
					HouseItem e = list.get(id);
					if (e == null) {
						e = new HouseItem(rs);
					}
					e.weight ++ ; // 增加权重
					list.put(id, e);
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
		
		Map<Integer, HouseItem> list = list(q);
		
		n = list.size();
		if (n == 0) {
			model.addAttribute("nothing", "抱歉, 啥也没找到");
		}
		pn = n / MAX + 1; // 最大页数
		model.addAttribute("pn", pn);
		
		SysUtil.log("搜索结果", list.size());
		
		model.addAttribute("list", list);
		
		return "/search.jsp";
	}
}
