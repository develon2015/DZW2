package em;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <pre>
CREATE TABLE IF NOT EXISTS order(
	id INT AUTO_INCREMENT, 
	hid INT NOT NULL,			#House ID
	uid INT NOT NULL,			#预定者
	times DATE NOT NULL,
	timee DATE NOT NULL,
	n INT NOT NULL,
	price DOUBLE NOT NULL,			#价格
	date DATETIIME DEFAULT NOW(),
	PRIMARY KEY(id)
)DEFAULT CHARSET=UTF8;
*</pre>
 */
public class Order {
	public int id;
	public int hid, uid, n;
	public double price;
	public Date times, timee;
	public Timestamp date;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Order(ResultSet rs) {
		create(rs);
	}
	
	private void create(ResultSet rs) {
		try {
			id = rs.getInt("id");
			hid = rs.getInt("hid");
			uid = rs.getInt("uid");
			price = rs.getDouble("price");
			n = rs.getInt("n");
			times = new Date(dateFormat.parse(rs.getString("times")).getTime());
			timee = new Date(dateFormat.parse(rs.getString("timee")).getTime());
			date = rs.getTimestamp("date");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
