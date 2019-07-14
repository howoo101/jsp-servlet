package login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login/logout")
public class logout extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 세션 삭제
      request.getSession().invalidate();
      System.out.println("sdfdfsdfsd");
      // index.jsp페이지로 보내준다(세션이 없는 채로) >> 로그아웃되어있을 것이다.
      response.sendRedirect("/login.jsp");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}