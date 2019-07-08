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
public class HouseController {
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
	
	private List<HouseItem> list(String query) {
		Map<Integer, HouseItem> list = new HashMap<Integer, HouseItem>();
		List<HouseItem> ls = new ArrayList<HouseItem>();
		String[] qs = query.split(" ");
		if (qs.length < 1)
			SysUtil.log("ɶҲ����");
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
		
		// ��list���б���, ����
		Set<Entry<Integer, HouseItem>> set = list.entrySet();
		for (Entry<Integer, HouseItem> en : set) {
			ls.add(en.getValue());
		}
		
		HouseItem enext = null;
		for (int i = 0; i < ls.size() - 1; i ++ ) {
			
			HouseItem e = ls.get(i); // ��ǰԪ��(0, 1, ..., n-2)
			int weight = e.weight;
			//System.out.printf("Ԫ��%s, ����%d ��ʼ����\n", e, weight);
			
			// n��[n+1, n-1]�Ƚ�
			for (int j = i + 1; j < ls.size(); j ++ ) {
				enext = ls.get(j);
				if (enext.weight > weight) { // ��������
					HouseItem tmp = e;
					ls.set(i, enext);
					ls.set(j, tmp);
					//System.out.printf("����%s��%s\n", e, enext);
				}
			}
		}
		return ls;
	}
	
	@RequestMapping("/house")
	public String search(
			@RequestParam(value = "id", required = true) int id,
			Model model) throws Exception {
		
		
		return "/house.jsp";
	}
}
