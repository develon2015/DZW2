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
	// �ϴ��ļ��洢Ŀ¼
	private static final String UPLOAD_DIRECTORY = "upload";

	// �ϴ�����
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
		// �жϵ�¼���
		User user = LoginController.getUser(request, response);
		if (user == null) {

			mv.addObject("result", "����δ��¼, ����<a href=\"" + request.getContextPath() + "/user/login.html\">��¼</a>���ٷ���������");
			return mv;
		}

		try {
			// ������
			if ("upload".equals(request.getParameter("request")) ) {
				List<FileItem> formItems = getItems(request); /* �� */
				// �ܾ�������
				if ("".equals(getParameter(formItems, "name").trim()) || "".equals(getParameter(formItems, "name").trim()) ) {
					mv.addObject("result", "����ϸ��д");
					return mv;
				}
				int ts = Integer.parseInt(getParameter(formItems, "time_short"));
				int tl = Integer.parseInt(getParameter(formItems, "time_long"));
				if (ts > tl) {
					mv.addObject("result", "��̳���ʱ�䲻�ܴ��������ʱ��");
					return mv;
				}
				boolean x = false;
				if (x)
				if (!getParameter(formItems, "tel_num").matches("^1[3578](\\d){9}$")) {
					mv.addObject("result", "��ϵ��ʽ����ȷ");
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
							mv.addObject("result", "���Ժ�����");
							return mv;
						}
						
						// �����ļ��ϴ�
						try {
							PreparedStatement psmt = DBI.getConnection().prepareStatement("SELECT id FROM house WHERE uid_master=? AND date=? AND name=?");
							psmt.setInt(1, user.getUid());
							psmt.setTimestamp(2, date);
							psmt.setString(3, getParameter(formItems, "name"));
							SysUtil.log("�ļ�����", psmt);
							ResultSet rs_id = psmt.executeQuery();
							if (!rs_id.next()) {
								SysUtil.log("��ѯhouse idʧ��");
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
							SysUtil.log("����image�ֶ�", psmt);
							r = psmt.executeUpdate();
							if (r != 1) {
								SysUtil.log("����image�ֶ�ʧ��");
							}
						} catch(Exception e) {
							SysUtil.log(e);
						}
						
						// ���ؽ��
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
		// ������ʱ·�����洢�ϴ����ļ�
		// ���·����Ե�ǰӦ�õ�Ŀ¼
		if (uploadPath == null) {
			uploadPath = request.getSession().getServletContext().getRealPath(File.separator) + UPLOAD_DIRECTORY;
			SysUtil.log("ȷ���ϴ�Ŀ¼", uploadPath);
			// ���Ŀ¼�������򴴽�
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
				SysUtil.log("�����ϴ�Ŀ¼");
			}
		}
		try {
			List<FileItem> list = upload.parseRequest(request);
			if (list == null || list.size() < 1) {
				SysUtil.log("list ����һ������", "��СΪ" + list.size() + "");
			}
			return list;
		} catch (FileUploadException e) {
			SysUtil.log(e);
		}
		return null;
	}

	// �����ϴ��ļ�
	private String saveImg(List<FileItem> formItems, HttpServletRequest request, HttpServletResponse response, String hid) throws IOException {
		try {
			List<String> list = new ArrayList<String>();
			// ����������
			for (FileItem item : formItems) {
				// �����ڱ��е��ֶ�
				if (!item.isFormField()) {
					if ("".equals(item.getName()) )
							continue;
					String fileName = new File(hid + "_" + item.getName()).getName().replaceAll(":", "_");
					String filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					// �����ļ���Ӳ��
					item.write(storeFile);
					SysUtil.log("�ϴ��ɹ�", filePath);
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
			request.setAttribute("message", "������Ϣ: " + ex.getMessage());
		}
		return null;
	}
	
	private static String uploadPath = null;
	private static ServletFileUpload upload = null;
	static {
		// �����ϴ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			// ������ʱ�洢Ŀ¼
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			upload = new ServletFileUpload(factory);
			// ��������ļ��ϴ�ֵ
			upload.setFileSizeMax(MAX_FILE_SIZE);
			// �����������ֵ (�����ļ��ͱ�����)
			upload.setSizeMax(MAX_REQUEST_SIZE);
			// ���Ĵ���
			upload.setHeaderEncoding("UTF-8");
	}

	private String getParameter(List<FileItem> formItems, String key) {
		// ����������
		for (FileItem item : formItems) {
			// �����ڱ��е��ֶ�
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
		return "ʧ�ܵ�ȡֵ";
	}
}
