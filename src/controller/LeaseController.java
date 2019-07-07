package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.SysUtil;
import em.User;

@Controller
public class LeaseController {
	// 上传文件存储目录
	private static final String UPLOAD_DIRECTORY = "upload";

	// 上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private static PreparedStatement psmt = null;
	private static final String sql = "INSERT INTO house"
			+ "(name, pn, time_short, time_long, area, price, "
			+ " address, info, tel_name, tel_num, uid_master, date) "
			+ "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	@RequestMapping("/lease")
	public ModelAndView lease(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/lease.jsp");
		// 判断登录情况
		User user = LoginController.getUser(request, response);
		if (user == null) {

			mv.addObject("result", "您尚未登录, 请先<a href=\"" + request.getContextPath() + "/user/login.html\">登录</a>后再发布出租屋");
			return mv;
		}

		try {
			// 处理发布
			if ("upload".equals(request.getParameter("request")) ) {
				List<FileItem> formItems = getItems(request); /* 表单 */
				// 拒绝服务检测
				if ("".equals(getParameter(formItems, "name").trim()) || "".equals(getParameter(formItems, "name").trim()) ) {
					mv.addObject("result", "请仔细填写");
					return mv;
				}
				int ts = Integer.parseInt(getParameter(formItems, "time_short"));
				int tl = Integer.parseInt(getParameter(formItems, "time_long"));
				if (ts > tl) {
					mv.addObject("result", "最短出租时间不能大于最长出租时间");
					return mv;
				}
				boolean x = false;
				if (x)
				if (!getParameter(formItems, "tel_num").matches("^1[3578](\\d){9}$")) {
					mv.addObject("result", "联系方式不正确");
					return mv;
				}
				
				try {
					// 更新数据库
					if (psmt == null || psmt.isClosed()) {
						psmt = DBI.getConnection().prepareStatement(sql);
					}
/*------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| 0id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| 1name       | varchar(255) | NO   |     | NULL    |                |
| 2pn         | int(11)      | NO   |     | NULL    |                |
| 3time_short | int(11)      | NO   |     | NULL    |                |
| 4time_long  | int(11)      | NO   |     | NULL    |                |
| 5area       | double       | NO   |     | NULL    |                |
| 6price      | double       | NO   |     | NULL    |                |
| 7address    | varchar(255) | NO   |     | NULL    |                |
| 8info       | text         | NO   |     | NULL    |                |
| 9tel_name   | varchar(255) | NO   |     | NULL    |                |
|10tel_num    | varchar(255) | NO   |     | NULL    |                |
| ?enable     | tinyint(1)   | YES  |     | 0       |                |
|11uid_master | int(11)      | NO   |     | NULL    |                |
| ?image      | text         | YES  |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------*/
						psmt.clearParameters();
						ResultSet rs = psmt.executeQuery("SELECT now()");
						Timestamp date = null;
						if (rs.next()) {
							date = rs.getTimestamp(1);
//							System.out.println(date);
						}
						psmt.clearParameters();
						psmt.setString(1, getParameter(formItems, "name"));
						psmt.setInt(2, Integer.parseInt(getParameter(formItems, "pn")) );
						psmt.setInt(3, Integer.parseInt(getParameter(formItems, "time_short")) );
						psmt.setInt(4, Integer.parseInt(getParameter(formItems, "time_long")) );
						psmt.setDouble(5, Double.parseDouble(getParameter(formItems, "area")) );
						psmt.setDouble(6, Double.parseDouble(getParameter(formItems, "price")) );
						psmt.setString(7, getParameter(formItems, "address"));
						psmt.setString(8, getParameter(formItems, "info"));
						psmt.setString(9, getParameter(formItems, "tel_name"));
						psmt.setString(10, getParameter(formItems, "tel_num"));
						psmt.setInt(11, user.getUid());
						psmt.setTimestamp(12, date);
						int r = psmt.executeUpdate();
						if (r != 1) {
							mv.addObject("result", "请稍后再试");
							return mv;
						}
						
						// 处理文件上传
						try {
							PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT id FROM house WHERE uid_master=? AND date=? AND name=?");
							psmt.setInt(1, user.getUid());
							psmt.setTimestamp(2, date);
							psmt.setString(3, getParameter(formItems, "name"));
							SysUtil.log("文件长传", psmt);
							ResultSet rs_id = psmt.executeQuery();
							if (!rs_id.next()) {
								SysUtil.log("查询house id失败");
								return mv;
							}
							int id = rs_id.getInt("id");
							System.out.println("id -> " + id);
							String image = saveImg(formItems, request, response, "" + id);
							SysUtil.log(image);
							psmt.close();
							psmt = DBI.getConnection().prepareStatement("UPDATE house SET image=? WHERE id=?");
							if (image == null || "".equals(image)) {
								psmt.setNull(1, java.sql.Types.VARCHAR);
							} else 
								psmt.setString(1, image);
							psmt.setInt(2, id);
							SysUtil.log("更新image字段", psmt);
							r = psmt.executeUpdate();
							if (r != 1) {
								SysUtil.log("更新image字段失败");
							}
						} catch(Exception e) {
							SysUtil.log(e);
						}
						
						// 返回结果
						mv.setViewName("/lease_succeed.jsp");
						return mv;
				} catch(SQLException e) {
					SysUtil.log(e);
					mv.addObject("result", e.getMessage());
					return mv;
				}
			}
		} catch(Exception e) {
			SysUtil.log(e);
			mv.setViewName("/lease.jsp");
			return mv;
		}
		return mv;
	}
	
	private List<FileItem> getItems(HttpServletRequest request) {
		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		if (uploadPath == null) {
			uploadPath = request.getSession().getServletContext().getRealPath(File.separator) + UPLOAD_DIRECTORY;
			SysUtil.log("确定上传目录", uploadPath);
			// 如果目录不存在则创建
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
				SysUtil.log("创建上传目录");
			}
		}
		try {
			List<FileItem> list = upload.parseRequest(request);
			if (list == null || list.size() < 1) {
				SysUtil.log("list 就是一个垃圾", "大小为" + list.size() + "");
			}
			return list;
		} catch (FileUploadException e) {
			SysUtil.log(e);
		}
		return null;
	}

	// 处理上传文件
	private String saveImg(List<FileItem> formItems, HttpServletRequest request, HttpServletResponse response, String hid) throws IOException {
		try {
			List<String> list = new ArrayList<String>();
			// 迭代表单数据
			for (FileItem item : formItems) {
				// 处理不在表单中的字段
				if (!item.isFormField()) {
					if ("".equals(item.getName()) )
							continue;
					String fileName = new File(hid + "_" + item.getName()).getName().replaceAll(":", "_");
					String filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					// 保存文件到硬盘
					item.write(storeFile);
					SysUtil.log("上传成功", filePath);
					list.add(fileName);
				}
			}
			
			String image = "";
			for (int i = 0; i < list.size(); i ++ ) {
				image += list.get(i) + ":";
			}
			
			SysUtil.log(image);
			return image;
		} catch (Exception ex) {
			request.setAttribute("message", "错误信息: " + ex.getMessage());
		}
		return null;
	}
	
	private static String uploadPath = null;
	private static ServletFileUpload upload = null;
	static {
		// 配置上传参数
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			// 设置临时存储目录
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			upload = new ServletFileUpload(factory);
			// 设置最大文件上传值
			upload.setFileSizeMax(MAX_FILE_SIZE);
			// 设置最大请求值 (包含文件和表单数据)
			upload.setSizeMax(MAX_REQUEST_SIZE);
			// 中文处理
			upload.setHeaderEncoding("UTF-8");
	}

	private String getParameter(List<FileItem> formItems, String key) {
		// 迭代表单数据
		for (FileItem item : formItems) {
			// 处理在表单中的字段
			if (item.isFormField()) {
				try {
					if (key.equals(item.getFieldName()) ) {
						return item.getString("UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return "失败的取值";
	}
}
