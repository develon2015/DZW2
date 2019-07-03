package common;

import java.util.ResourceBundle;

public class SysUtil {
	private static final String CONFIG_FILE_NAME = "config";
	private static final ResourceBundle RES_BUNDLE = ResourceBundle.getBundle(CONFIG_FILE_NAME);
	
	static {
		try {
			System.out.println("OK" + RES_BUNDLE.getString("db_url"));
		} catch(Throwable e) {
			log(e);
		}
	}
	
	public static boolean test() {
		return DBI.test();
	}
	
	public static void log(Throwable e) {
		System.out.println("发生了异常 -> " + " (" + e.toString() + ")");
	}
	
	public static void log(String e) {
		System.out.println("[I] " + e);
	}

	public static void log(String label, String e) {
		System.out.println("[I] " + label + " -> " + e);
	}
	
	/**
	 * 获取配置文件中的字符值
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return RES_BUNDLE.getString(key);
	}
}
