package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBI {
	private static Connection conn = null;
	
	static {
		// 加载数据库驱动类库
		try {
			Class.forName(SysUtil.get("jdbc_driver")).newInstance();
		} catch (Throwable e) {
			SysUtil.log(e);
		}
		
		try {
			conn = DriverManager.getConnection(SysUtil.get("db_url"));
		} catch(Throwable e) {
			SysUtil.log(e);
		}
	}
	
	public static boolean isValid() {
		try {
			if (conn.isValid(0)) {
				return true;
			}
		} catch (SQLException e) {
			SysUtil.log(e);
		}
		return false;
	}
	
	/**
	 * 返回数据库连接, 如果数据库不可用, 则重新连接
	 * @return
	 */
	public static Connection getConnection() {
		try {
			if (conn == null || !conn.isValid(0)) {
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
