package foodcourt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import db.Connector;
import vo.menuVo;

/**
 * Servlet implementation class MenuAajaxController
 */
@WebServlet("/MenuAajaxController")
public class MenuAajaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		System.out.println("�ڡڡڡڡڡڡ� url=" + url);
		Map<Integer, List<menuVo>> map = new HashMap<>();
		List<menuVo> list = new ArrayList<>();
		Connector con = new Connector();
		Connection conn = con.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		JSONObject obj = new JSONObject();

		try {
			stmt = conn.createStatement();
			String query = "";
			System.out.println(url);
			if (url.equals("0"))
				query = "SELECT * FROM foodcourt.menu";
			else if (url.equals("6"))
				query = "select * from foodcourt.menu order by rand() limit 1";
			else
				query = "SELECT * FROM foodcourt.menu where storeCode = " + url;

			System.out.println("�ڡڡڡڡڡڡ� query = " + query);
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				menuVo menu = new menuVo();
				menu.setMenuCode(rs.getInt("menuCode"));
				menu.setMenuName(rs.getString("menuName"));
				menu.setMenuPrice(rs.getInt("menuPrice"));
				menu.setStoreCode(rs.getInt("storeCode"));
				menu.setUri(rs.getString("uri"));
				if (!url.equals("0")) { // Ư�� �������� ������ ���
					map.computeIfAbsent(rs.getInt("storeCode"), k -> new ArrayList<menuVo>()).add(menu);
				} else { // ����(��ü���� �� ���
					list.add(menu);
				}
			}

			if (!url.equals("0")) { // Ư�� �������� ������ ���
				for (int i : map.keySet()) {
					obj.put("storeCode", i);
					obj.put("values", map.get(i));
				}
			} else { // ������ ������ ��� gson �Ľ�������
				obj.put("storeCode", 0); // ��ü ��� �����ö��� �ڵ带 0���� ����
				obj.put("values", list);
				System.out.println(obj.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("json: " + obj.toJSONString());

		// json�� �ѱ�
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(obj.toJSONString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
