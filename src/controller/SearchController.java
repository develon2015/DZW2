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
	private static final String ALL = "ȫ��";
	private static PreparedStatement psmt = null;
//	private static PreparedStatement psmtn = null;
	
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?)";
	
//	private static final String sqln = // ��ѯ�����
//			"SELECT COUNT(*) FROM house WHERE enable=FALSE AND " +
//			"(name LIKE ? OR address LIKE ?)";
	
	private static final int MAX = 8; // ÿҳ�������
	
	private Map<Integer, HouseItem> list(String query) {
		Map<Integer, HouseItem> list = new HashMap<Integer, HouseItem>();
		String[] qs = query.split(" ");
		if (qs.length < 1)
			SysUtil.log("ɶҲ����");
		for (String q : qs) {
			try {
				if (psmt == null || psmt.isClosed()) {
					psmt = DBI.getConnection().prepareStatement(sql);
				}
				psmt.clearParameters();
				psmt.setString(1, String.format("%%%s%%", q));
				psmt.setString(2, String.format("%%%s%%", q));
				SysUtil.log("����", psmt);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id");
					HouseItem e = list.get(id);
					if (e == null) {
						e = new HouseItem(rs);
					}
					e.weight ++ ; // ����Ȩ��
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
		int n = 0; // �����������
		int pn = 0; // ���ҳ��
		
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
			model.addAttribute("nothing", "��Ǹ, ɶҲû�ҵ�");
		}
		pn = n / MAX + 1; // ���ҳ��
		model.addAttribute("pn", pn);
		
		SysUtil.log("�������", list.size());
		
		model.addAttribute("list", list);
		
		return "/search.jsp";
	}
}
