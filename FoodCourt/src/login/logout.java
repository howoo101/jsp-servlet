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
      // ���� ����
      request.getSession().invalidate();
      System.out.println("sdfdfsdfsd");
      // index.jsp�������� �����ش�(������ ���� ä��) >> �α׾ƿ��Ǿ����� ���̴�.
      response.sendRedirect("/login.jsp");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}