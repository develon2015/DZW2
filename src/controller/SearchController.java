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
	private static final String ALL = "ȫ��";
	private static PreparedStatement psmt = null;
	private static PreparedStatement psmtn = null;
	
	private static final String sql = 
			"SELECT * FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?) LIMIT ?, ?";
	
	private static final String sqln = // ��ѯ�����
			"SELECT COUNT(*) FROM house WHERE enable=FALSE AND " +
			"(name LIKE ? OR address LIKE ?)";
	
	private static final int MAX = 8; // ÿҳ�������
	
	private List<HouseItem> list(String query) {
		List<HouseItem> list = new ArrayList<HouseItem>();
		String[] qs = query.split(" ");
		for (String q : qs) {
			try {
				// ���Ȳ�ѯ����, ����ҳ������pn
				if (psmtn == null || psmtn.isClosed()) {
					psmtn = DBI.getConnection().prepareStatement(sqln);
				}
				psmtn.clearParameters();
				psmtn.setString(1, String.format("%%%s%%", q));
				psmtn.setString(2, String.format("%%%s%%", q));
				ResultSet rsn = psmtn.executeQuery();
	
				
				// limit��ѯ
				if (psmt == null || psmt.isClosed()) {
					psmt = DBI.getConnection().prepareStatement(sql);
				}
				psmt.clearParameters();
				psmt.setString(1, String.format("%%%s%%", q));
				psmt.setString(2, String.format("%%%s%%", q));
	//			psmt.setInt(3, (page - 1) * MAX);
				psmt.setInt(4, MAX);
				SysUtil.log("����", psmt);
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
		int n = 0; // �����������
		int pn = 0; // ���ҳ��
		
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
			model.addAttribute("nothing", "��Ǹ, ɶҲû�ҵ�");
		}
		pn = n / MAX + 1; // ���ҳ��
		model.addAttribute("pn", pn);
		
		SysUtil.log("�������", list.size());
		
		for (HouseItem s : list) {
			//SysUtil.log("list", s);
		}
		
		model.addAttribute("list", list);
		
		return "/search.jsp";
	}
}
