package foodcourt;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class menuAction
 */
@WebServlet("/menuAction")
public class menuAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		if(url == null) url = "3";
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
			if (url == null)
				query = "SELECT * FROM foodcourt.menu";
			else
				query = "SELECT * FROM foodcourt.menu where storeCode = " + url;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				menuVo menu = new menuVo();
				menu.setMenuCode(rs.getInt("menuCode"));
				menu.setMenuName(rs.getString("menuName"));
				menu.setMenuPrice(rs.getInt("menuPrice"));
				menu.setStoreCode(rs.getInt("storeCode"));
				menu.setUri(rs.getString("uri"));
				map.computeIfAbsent(rs.getInt("storeCode"), k->new ArrayList<menuVo>()).add(menu);
			}
			for(int i : map.keySet()) {
				obj.put("storeCode", i);
				obj.put("values", map.get(i));
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
//		  String json = new Gson().toJson(map.get(Integer.parseInt(url)));
//		  json = json.substring(1,json.length()-1);
		
		String json = new Gson().toJson(map);
//		System.out.println(map);
//		System.out.println("json: " + obj.toJSONString());
		request.setAttribute("url", url);
		request.setAttribute("list", map.get(3));
		request.setAttribute("map", map);
		request.setAttribute("json", new Gson().toJson(obj));
//		RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
//		dis.forward(request, response);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		URLDecoder.decode(json,"UTF-8"); 
		out.write(new Gson().toJson(obj));
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
