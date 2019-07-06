package controller;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.DBI;
import common.SysUtil;
import user.User;

@Controller
public class LeaseController {
	// 上传文件存储目录
	private static final String UPLOAD_DIRECTORY = "upload";

	// 上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private void logic() {
		//
	}

	private static PreparedStatement psmt = null;
	private static final String sql = "INSERT INTO house"
			+ "(name, pn, time_short, time_long, area, price, "
			+ " address, info, tel_name, tel_num, uid_master) "
			+ "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				// 拒绝服务检测
				if ("".equals(request.getParameter("name").trim()) || "".equals(request.getParameter("name").trim()) ) {
					mv.addObject("result", "请仔细填写");
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
						psmt.setString(1, request.getParameter("name"));
						psmt.setInt(2, Integer.parseInt(request.getParameter("pn")) );
						psmt.setInt(3, Integer.parseInt(request.getParameter("time_short")) );
						psmt.setInt(4, Integer.parseInt(request.getParameter("time_long")) );
						psmt.setDouble(5, Double.parseDouble(request.getParameter("area")) );
						psmt.setDouble(6, Double.parseDouble(request.getParameter("price")) );
						psmt.setString(7, request.getParameter("address"));
						psmt.setString(8, request.getParameter("info"));
						psmt.setString(9, request.getParameter("tel_name"));
						psmt.setString(10, request.getParameter("tel_num"));
						psmt.setInt(11, user.getUid());
						int r = psmt.executeUpdate();
						if (r != 1) {
							mv.addObject("result", "请稍后再试");
							return mv;
						}
						// 返回结果
						mv.addObject("result", "成功!");
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

	// 处理上传文件
	private void saveImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 如果不是则停止
			return;
		}

		// 配置上传参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 设置临时存储目录
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// 中文处理
		upload.setHeaderEncoding("UTF-8");

		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = request.getSession().getServletContext().getRealPath(File.separator) + UPLOAD_DIRECTORY;

		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// 解析请求的内容提取文件数据
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// 迭代表单数据
				for (FileItem item : formItems) {
					// 处理不在表单中的字段
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						// 在控制台输出文件的上传路径
						System.out.println(filePath);
						// 保存文件到硬盘
						item.write(storeFile);
						request.setAttribute("message", "文件上传成功!");
					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "错误信息: " + ex.getMessage());
		}
		// 跳转到 message.jsp

	}
}
