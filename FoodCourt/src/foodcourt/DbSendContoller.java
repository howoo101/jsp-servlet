package foodcourt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import db.Connector;


@WebServlet("/DbSendController")
public class DbSendContoller extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Calendar date = Calendar.getInstance();
		String[] data = req.getParameterValues("data");
		System.out.println(Arrays.toString(data));
//		Date d = new Date(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
		
		Date d = new Date();
		SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdfm.format(d);
		System.out.println(dateStr);
		JsonParser parser = new JsonParser();
		
		
		Connection con = new Connector().getConn();
		
		String sql = "";
		PreparedStatement pstmt = null;
		try {
			for(int i = 0; i < data.length; i++) {
				JsonElement element = parser.parse(data[i]);
				String menuCode = element.getAsJsonObject().get("itemCode").getAsString();
				String qntStr = element.getAsJsonObject().get("qnt").getAsString();
				int qnt = Integer.parseInt(qntStr);
				for(int j = 0; j < qnt; j++) {
					sql = "insert into foodcourt.revenue(menuCode,rDate) values(?,?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(menuCode));
					pstmt.setString(2,  dateStr);
					pstmt.executeUpdate();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!= null) try{pstmt.close();}catch(Exception e) {}
			if(con!= null) try{con.close();}catch(Exception e) {}
		}
		resp.sendRedirect("/");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
