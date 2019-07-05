package controller;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private static final String sql = "";

	@RequestMapping("/lease")
	public ModelAndView lease(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("/lease.jsp");
		// ������
		if ("upload".equals(request.getParameter("request")) ) {
			// �жϵ�¼���
			User user = LoginController.getUser(request, response);
			if (user == null) {
//				mv.setViewName("redirect:/user/login.html");
//				return mv;
			}
			
			/*
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| name       | varchar(255) | NO   |     | NULL    |                |
| area       | double       | NO   |     | NULL    |                |
| time_short | int(11)      | NO   |     | NULL    |                |
| time_long  | int(11)      | NO   |     | NULL    |                |
| price      | double       | NO   |     | NULL    |                |
| info       | text         | NO   |     | NULL    |                |
| tel_name   | varchar(255) | NO   |     | NULL    |                |
| tel_num    | varchar(255) | NO   |     | NULL    |                |
| enable     | tinyint(1)   | YES  |     | 0       |                |
| uid_master | int(11)      | NO   |     | NULL    |                |
| uid_guest  | int(11)      | YES  |     | NULL    |                |
| image      | text         | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
			 */
			
			try {
				if (psmt == null || psmt.isClosed()) {
					psmt = DBI.getConnection().prepareStatement(sql);
				}
			} catch(Exception e) {
				SysUtil.log(e);
				mv.addObject("result", "ʧ��");
				return mv;
			}
			
			try {
				//saveImg(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mv.addObject("result", "ʧ��");
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
