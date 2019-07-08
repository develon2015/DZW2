package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.*;

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
	
	private List<HouseItem> list(String query) {
		Map<Integer, HouseItem> list = new HashMap<Integer, HouseItem>();
		List<HouseItem> ls = new ArrayList<HouseItem>();
		String[] qs = query.split(" ");
		if (qs.length < 1)
			SysUtil.log("啥也不干");
		for (String q : qs) {
			if (qs.length == 1 && "#ALL".equals(q)) {
				q = "%";
			} else {
				q = q.replaceAll("%", "\\\\%");
				q = q.replaceAll("_", "\\\\_");
			}
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
		
		// 对list进行遍历, 排序
		Set<Entry<Integer, HouseItem>> set = list.entrySet();
		for (Entry<Integer, HouseItem> en : set) {
			ls.add(en.getValue());
		}
		
		HouseItem enext = null;
		for (int i = 0; i < ls.size() - 1; i ++ ) {
			
			HouseItem e = ls.get(i); // 当前元素(0, 1, ..., n-2)
			int weight = e.weight;
			//System.out.printf("元素%s, 比重%d 开始交换\n", e, weight);
			
			// n与[n+1, n-1]比较
			for (int j = i + 1; j < ls.size(); j ++ ) {
				enext = ls.get(j);
				if (enext.weight > weight) { // 交换次序
					HouseItem tmp = e;
					ls.set(i, enext);
					ls.set(j, tmp);
					//System.out.printf("交换%s和%s\n", e, enext);
				}
			}
		}
		return ls;
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
			q = "#ALL";
		}
		
		List<HouseItem> list = list(q);
		int sub = (page - 1) * MAX;
		int sup = page * MAX;
		sub = sub > list.size() ? 0 : sub;
		sup = sup > list.size() ? list.size() : sup;
		List<HouseItem> list_limit = list.subList(sub, sup);
		
		n = list.size();
		if (n == 0) {
			model.addAttribute("nothing", "抱歉, 啥也没找到");
		}
		pn = n / MAX + (n % MAX == 0 ? 0 : 1); // 最大页数
		SysUtil.log("最大页数", "" + pn);
		model.addAttribute("pn", pn);
		
		SysUtil.log("搜索结果", list.size());
		
		model.addAttribute("list", list_limit);
		
		return "/search.jsp";
	}
}
