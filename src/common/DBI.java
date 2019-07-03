package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBI {
	private static Connection conn = null;
	
	static {
		// 加载数据库驱动类库
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Throwable e) {
			SysUtil.log(e);
		}
		
		try {
			conn = DriverManager.getConnection(SysUtil.get("db_url"));
		} catch(Throwable e) {
			SysUtil.log(e);
		}
	}
	
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(SysUtil.get("db_url"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		return conn;
	}

	/**
	 * 测试数据库可用性
	 * @return
	 */
	public static boolean test() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(SysUtil.get("db_url"));
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			try { conn.close(); } catch(Throwable e) {};
		}
		return true;
	}
}
