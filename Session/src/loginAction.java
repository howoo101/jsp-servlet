
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class loginAction
 */
@WebServlet("/loginAction")
public class loginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String mem = request.getParameter("mem");

		// 어느페이지에서 login해서 왔는지
		String caller = request.getParameter("caller");

		// 세션설정
//		request.getSession().setAttribute(name, value);
		Cookie cookie = null;

		// id pwd 체크
		if (id != null && pwd != null && id.equals("bit") && pwd.equals("1234")) {
			// 로그인 성공했으면 session
			HttpSession session = request.getSession();
			session.setAttribute("login", id);
			// 로그인 했을때만 쿠키를 다룰수있게 (yes24랑 똑같이)
			Cookie[] cookies = request.getCookies();
			
			for (int i = 0; i < cookies.length; i++) {
				// cookie id받아서 담는다.
				cookie = new Cookie("mem", id);
				if (mem != null) {
					// 아이디가 쿠키에저장된 아이디랑 다를때 쿠키에 저장된 아이디로 쿠키를 바꿔준다.
					if (cookies[i].getName().equals("mem") && !cookies[i].getValue().equals(id)) {
						cookie = new Cookie("mem", cookies[i].getValue());
					}
				} else { // id기억 체크 안되어 있을때
					cookie.setMaxAge(0);
				}
			}
			
			response.addCookie(cookie);
			response.sendRedirect(caller);
		} else {
			response.sendRedirect("/login.jsp?from=" + caller);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
