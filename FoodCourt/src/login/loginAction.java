package login;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.Connector;
import encryption.Encryption;
import vo.tableVo;

@WebServlet("/login/loginAction")
public class loginAction extends HttpServlet {


   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // pwd�� ��ȣȭ�� Ŭ���� ����
      Encryption encodingPwd = new Encryption();

      // �α��ν� �Է��ߴ� id, pwd �޾ƿ´�.
      String id = request.getParameter("id");
      String pwdBeforeEncoding = request.getParameter("pwd");
      
      // �޾ƿ� pwd�� ��ȣȭ�� ������ ����
      String pwd = encodingPwd.pwdToSha2(pwdBeforeEncoding);

      // db�� ����
      Connection conn = new Connector().getConn();
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      // id�� pw�� db�� �ִ� ������ ������ ��ġ���� Ȯ��
      // �ϴ� id, pwdâ�� null�� ���������� Ȯ��
      if (id != null && pwd != null) {
         System.out.println("okkkkay1");
         try {
            pstmt = conn.prepareStatement("select * from owner where ownerId=? and ownerPwd=?;");
            System.out.println("okkkkay2");
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            System.out.println("okkkkay3");
            rs = pstmt.executeQuery();
            System.out.println("okkkkay4");
            
            // ���� ������ >> login.jsp (�Է� ���� id�� pwd�� �������� �ʴ� ������ ���)
            String pageTo = "/login.jsp";

            // �Է��� id�� pwd�� db��(owner���̺�) �����Ѵٸ�
            // rs�� ����Ǿ��ִ� ����� �͵��� ������ list�� ����� ��ȯ
            if (rs.next()) {
               // �Է¹޾Ҵ� id�� pwd�� �����ϸ� getInfoTable() ȣ��
               //  >> ���� ���� ���ڿ�(���̺� �� ���� �����)�� rows������ �޴´�.
               tableVo insertInfo = getInfoTable(rs.getString("StoreCode"), conn);
               
               Cookie cookie = new Cookie("newInsert", URLEncoder.encode(insertInfo.toString(), "utf-8"));
               cookie.setPath("/manage.jsp");
               response.addCookie(cookie);
               System.out.println("��Ű���� >> "+cookie.getValue());
               
               // ���� ������ >> manage.jsp (�Է� ���� id�� pwd�� db�� �����ϴ� ���)
               pageTo = "/manage.jsp";
               
               // ���� ����
               HttpSession session = request.getSession();
               session.setAttribute("loginOk", "true");
            }

            // ���̺� id�� pwd�� ������ login.jsp�� �ٽ� �����ش�.
            response.sendRedirect(pageTo);

         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            if(rs!=null) 
               try { rs.close();} catch (SQLException e) {e.printStackTrace();}
            
            if(pstmt!=null) 
               try { pstmt.close();} catch (SQLException e) {e.printStackTrace();}
            
            if(conn!=null) 
               try { conn.close();} catch (SQLException e) {e.printStackTrace();}
            
         }

      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

   protected tableVo getInfoTable(String storeCode, Connection conn) {
      // ��ȯ�� ��(���� �� ����, ������ ���̺� �ڵ�)�� ���� ��ü
      tableVo insertInfo = new tableVo();
      
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      PreparedStatement pstmt2 = null;
      ResultSet rs2 = null;
      
      String tmp = "";
      int sum = 0; // 

      // (��ü �ֹ��� ����)�޴���, �ֱ� �ֹ���, �� �ֹ� ����, �� ����, �����ڵ�
      String query = "select menuName, last_order, cnt, cnt*menuPrice as totalRevenue, m.storeCode from (select menuCode, max(date_format(rDate, '%Y.%m.%d')) as last_order, count(*) as cnt from revenue group by menuCode) r1, menu m where r1.menuCode = m.menuCode";

      try {
         // (Ư�� �� ���Կ� ����)�޴���, �ֱ� �ֹ���, �� �ֹ� ����, �� ����, �����ڵ�
         pstmt = conn.prepareStatement("select * from (" + query + ") mm where mm.storeCode=?;");
         pstmt.setString(1, storeCode);
         rs = pstmt.executeQuery();
         
         // ���������� �޾ƿ� ������ ����Ʈ�� ���� �����Ѵ�.
         while (rs.next()) {
            tmp += "<tr><td>" + rs.getString("menuName") + "</td><td>" + rs.getString("cnt") + "</td><td>" + rs.getInt("totalRevenue")
            + "</td><td>" + rs.getString("last_order") + "</td></tr>";
            
            sum += rs.getInt("totalRevenue");
         }
         
         // 1. ��ȯ�� ��ü�� �Ѽ��� �־��ֱ�
         insertInfo.setTotRevenue(sum);
         
         // 2. ��ȯ�� ��ü�� ���̺� ������ ������ ���� html�ڵ� �־��ֱ�
         insertInfo.setTableRows(tmp);
      
         // ���Ը��� �޾ƿ��� ����
         pstmt2 = conn.prepareStatement("select storeName from store where storeCode=?;");
         pstmt2.setString(1,storeCode); // ��Ű������ �Ѱܹ��� storeCode�� ������ ���ǿ� �ִ´�.(storeCode�� �´� ���Ը�)
         rs2 = pstmt2.executeQuery();
         
         // 3. ��ȯ�� ��ü�� ���Ը� �־��ֱ�
         if (rs2.next()) {
            insertInfo.setStoreName(rs2.getString("storeName"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally { // �ڿ� ��ȯ
         if(rs!=null) 
            try { rs.close();} catch (SQLException e) {e.printStackTrace();}
         if(pstmt!=null) 
            try { pstmt.close();} catch (SQLException e) {e.printStackTrace();}
         if(rs2!=null) 
            try { rs2.close();} catch (SQLException e) {e.printStackTrace();}
         if(pstmt2!=null) 
            try { pstmt2.close();} catch (SQLException e) {e.printStackTrace();}
      }
      
      return insertInfo;

   } // getInfoTable()

}