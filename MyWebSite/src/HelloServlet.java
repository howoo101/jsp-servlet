

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/Hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		out.println("<!DOCTYPE html>");
		out.println("<html lang='en'>");
		out.println("<head>");
		out.println("    <meta charset='UTF-8'>");
		out.println("    <title>Document</title>");
		out.println("</head>");
		if(id == null && pwd == null) {
			out.println("<h1>id pwd 입력바람</h1>");
		}else {
			if(id.equals("bit") && pwd.equals("1234")) {
				out.println("맞았습니다.");
			}else {
				out.println("틀렸습니다.");
				out.println("id= "+id+"<br>");
				out.println("pwd= "+pwd);
			}
		}
		
	}


}
