package em;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int uid = 0;
	private String name;
	
	public User(int uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public User(ResultSet rs) {
		try {
			this.uid = rs.getInt("uid");
			this.name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
