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
	// �ϴ��ļ��洢Ŀ¼
	private static final String UPLOAD_DIRECTORY = "upload";

	// �ϴ�����
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
		// �жϵ�¼���
		User user = LoginController.getUser(request, response);
		if (user == null) {

			mv.addObject("result", "����δ��¼, ����<a href=\"" + request.getContextPath() + "/user/login.html\">��¼</a>���ٷ���������");
			return mv;
		}
		
		try {
			// ������
			if ("upload".equals(request.getParameter("request")) ) {
				// �ܾ�������
				if ("".equals(request.getParameter("name").trim()) || "".equals(request.getParameter("name").trim()) ) {
					mv.addObject("result", "����ϸ��д");
					return mv;
				}
				
				try {
					// �������ݿ�
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
							mv.addObject("result", "���Ժ�����");
							return mv;
						}
						// ���ؽ��
						mv.addObject("result", "�ɹ�!");
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

	// �����ϴ��ļ�
	private void saveImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ����Ƿ�Ϊ��ý���ϴ�
		if (!ServletFileUpload.isMultipartContent(request)) {
			// ���������ֹͣ
			return;
		}

		// �����ϴ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// ������ʱ�洢Ŀ¼
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// ��������ļ��ϴ�ֵ
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// �����������ֵ (�����ļ��ͱ�����)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// ���Ĵ���
		upload.setHeaderEncoding("UTF-8");

		// ������ʱ·�����洢�ϴ����ļ�
		// ���·����Ե�ǰӦ�õ�Ŀ¼
		String uploadPath = request.getSession().getServletContext().getRealPath(File.separator) + UPLOAD_DIRECTORY;

		// ���Ŀ¼�������򴴽�
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// ���������������ȡ�ļ�����
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// ����������
				for (FileItem item : formItems) {
					// �����ڱ��е��ֶ�
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						// �ڿ���̨����ļ����ϴ�·��
						System.out.println(filePath);
						// �����ļ���Ӳ��
						item.write(storeFile);
						request.setAttribute("message", "�ļ��ϴ��ɹ�!");
					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "������Ϣ: " + ex.getMessage());
		}
		// ��ת�� message.jsp

	}
}
