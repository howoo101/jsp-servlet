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
      // pwd를 암호화할 클래스 선언
      Encryption encodingPwd = new Encryption();

      // 로그인시 입력했던 id, pwd 받아온다.
      String id = request.getParameter("id");
      String pwdBeforeEncoding = request.getParameter("pwd");
      
      // 받아온 pwd를 암호화한 값으로 변경
      String pwd = encodingPwd.pwdToSha2(pwdBeforeEncoding);

      // db와 연결
      Connection conn = new Connector().getConn();
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      // id와 pw가 db에 있는 점주의 계정과 일치여부 확인
      // 일단 id, pwd창에 null값 들어갔는지부터 확인
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
            
            // 다음 페이지 >> login.jsp (입력 받은 id와 pwd가 존재하지 않는 유저인 경우)
            String pageTo = "/login.jsp";

            // 입력한 id와 pwd가 db상에(owner테이블) 존재한다면
            // rs에 저장되어있는 실행된 것들을 꺼내서 list로 만들어 반환
            if (rs.next()) {
               // 입력받았던 id와 pwd가 존재하면 getInfoTable() 호출
               //  >> 리턴 받은 문자열(테이블에 들어갈 행의 내용들)을 rows변수에 받는다.
               tableVo insertInfo = getInfoTable(rs.getString("StoreCode"), conn);
               
               Cookie cookie = new Cookie("newInsert", URLEncoder.encode(insertInfo.toString(), "utf-8"));
               cookie.setPath("/manage.jsp");
               response.addCookie(cookie);
               System.out.println("쿠키굽굽 >> "+cookie.getValue());
               
               // 다음 페이지 >> manage.jsp (입력 받은 id와 pwd가 db상에 존재하는 경우)
               pageTo = "/manage.jsp";
               
               // 세션 생성
               HttpSession session = request.getSession();
               session.setAttribute("loginOk", "true");
            }

            // 테이블에 id와 pwd가 없으면 login.jsp로 다시 보내준다.
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
      // 반환할 값(가게 총 매출, 삽입할 테이블 코드)을 담을 객체
      tableVo insertInfo = new tableVo();
      
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      PreparedStatement pstmt2 = null;
      ResultSet rs2 = null;
      
      String tmp = "";
      int sum = 0; // 

      // (전체 주문에 대한)메뉴명, 최근 주문일, 총 주문 수량, 총 수입, 가게코드
      String query = "select menuName, last_order, cnt, cnt*menuPrice as totalRevenue, m.storeCode from (select menuCode, max(date_format(rDate, '%Y.%m.%d')) as last_order, count(*) as cnt from revenue group by menuCode) r1, menu m where r1.menuCode = m.menuCode";

      try {
         // (특정 한 가게에 대한)메뉴명, 최근 주문일, 총 주문 수량, 총 수입, 가게코드
         pstmt = conn.prepareStatement("select * from (" + query + ") mm where mm.storeCode=?;");
         pstmt.setString(1, storeCode);
         rs = pstmt.executeQuery();
         
         // 쿼리문으로 받아온 값들을 리스트에 각각 저장한다.
         while (rs.next()) {
            tmp += "<tr><td>" + rs.getString("menuName") + "</td><td>" + rs.getString("cnt") + "</td><td>" + rs.getInt("totalRevenue")
            + "</td><td>" + rs.getString("last_order") + "</td></tr>";
            
            sum += rs.getInt("totalRevenue");
         }
         
         // 1. 반환될 객체에 총수입 넣어주기
         insertInfo.setTotRevenue(sum);
         
         // 2. 반환될 객체에 테이블에 삽입할 내용을 담은 html코드 넣어주기
         insertInfo.setTableRows(tmp);
      
         // 가게명을 받아오는 쿼리
         pstmt2 = conn.prepareStatement("select storeName from store where storeCode=?;");
         pstmt2.setString(1,storeCode); // 쿠키값으로 넘겨받은 storeCode를 쿼리문 조건에 넣는다.(storeCode에 맞는 가게명)
         rs2 = pstmt2.executeQuery();
         
         // 3. 반환할 객체에 가게명 넣어주기
         if (rs2.next()) {
            insertInfo.setStoreName(rs2.getString("storeName"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally { // 자원 반환
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