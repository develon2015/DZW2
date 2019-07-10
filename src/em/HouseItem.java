package em;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import common.DBI;
import common.SysUtil;

public class HouseItem {
	public int weight; /* 优先级 */
	public String[] imgs;
	
	public int id;
	public int uid;
	public int pn;
	public int time_short;
	public int time_long;
	
	public String name;
	public String address;
	public String info;
	public String tel_name;
	public String tel_num;
	public String icon;
	public double price;
	public double area;
	
	public int enable; // 0审核中 1通过 2拒绝
	public Timestamp date;
	
	private static final String sql = 
			"SELECT * FROM house WHERE " +
			"id=?";
	
	public HouseItem(ResultSet rs) {
		createFromRS(rs);
	}
	
	public HouseItem(int id) {
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement(sql);
			psmt.clearParameters();
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				createFromRS(rs);
			}
		} catch(Exception e) {
			SysUtil.log(e);
		}
	}
	
	private void createFromRS(ResultSet rs) {
		try {
			id = rs.getInt("id");
			uid = rs.getInt("uid_master");
			pn = rs.getInt("pn");
			time_short = rs.getInt("time_short");
			time_long = rs.getInt("time_long");
			
			name = rs.getString("name");
			info = rs.getString("info");
			tel_name = rs.getString("tel_name");
			tel_num = rs.getString("tel_num");

			price = rs.getDouble("price");
			area = rs.getDouble("area");
			address = rs.getString("address");
			
			enable = rs.getInt("enable");
			date = rs.getTimestamp("date");
			
			String img = rs.getString("image");
			if (img != null) {
				imgs = img.split(":");
				for (int i = 0; i < imgs.length; i ++ ) {
					imgs[i] = SysUtil.get("path") + "/upload/" + imgs[i];
				}
				icon = imgs[0]; // 用第一张图片作为
			} else {
				imgs = new String[0];
				icon = SysUtil.get("path") + "/res/default.jpg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String p = String.format("<img src='%s'/><span>%s</span>", imgs[0], name);
		System.out.println(p);
		return p;
	}
}
