package em;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.DBI;
import common.SysUtil;

public class User implements Cloneable {
	private int uid = 0;
	private String name;
	private String phone;
	private String email;
	
	public User(int id) {
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM user WHERE uid=?");
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				create(rs);
			}
		} catch(Exception e) {
			SysUtil.log(e);
		}
	}
	
	public User(ResultSet rs) {
		create(rs);
	}
	
	private void create(ResultSet rs) {
		try {
			uid = rs.getInt("uid");
			name = rs.getString("name");
			phone = rs.getString("phone");
			email = rs.getString("email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void update() throws Exception {
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT * FROM user WHERE uid=?");
			psmt.setInt(1, uid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				create(rs);
			}
		} catch(Exception e) {
			SysUtil.log(e);
			throw e;
		}
	}

	public boolean updateDB() throws Exception {
		try {
			PreparedStatement psmt = DBI.getConnection().prepareStatement(
					"UPDATE user SET name=?, phone=?, email=? WHERE uid=?");
			psmt.setString(1, name);
			psmt.setString(2, phone);
			psmt.setString(3, email);
			psmt.setInt(4, uid);
			SysUtil.log("更新数据库", psmt);
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch(Exception e) {
			SysUtil.log(e);
			throw new RuntimeException(e.getMessage()
					.matches("Duplicate entry .+ for key 'name'") ? "用户名已被使用" : e.getMessage());
		}
		return false;
	}
	
	@Override
	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}
}
