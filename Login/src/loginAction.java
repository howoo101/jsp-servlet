

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginAction
 */
@WebServlet("/loginAction")
public class loginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String chk = request.getParameter("mem");
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length >0) {
			Cookie cookie = new Cookie("mem",chk);
			response.addCookie(cookie);
		}else {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("mem")) {
					Cookie cookie = new Cookie("mem",chk);
					response.addCookie(cookie);
				}
			}
		}
		
		
		if(id!= null && pwd != null && id.equals("bit") && pwd.equals("1234")) {
			response.sendRedirect("/");
		}else {
			System.out.println(chk+"  "+id);
			Cookie cookie = new Cookie("id",id);
			response.addCookie(cookie);
			response.sendRedirect("/login.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
