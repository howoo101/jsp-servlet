package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));
		String op = req.getParameter("op");
		System.out.println(op);
		res.setContentType("text/plain");
		res.setCharacterEncoding("utf-8");
		PrintWriter writer = res.getWriter();
		long result = 0;
		
		switch(op) {
			case "+": result = a+b; break;
			case "-": result = a-b; break;
			case "*": result = a*b; break;
			case "/": result = a/b; break;
			//default : throw new IllegalArgumentException("이상한거 입력하지마");
		}
		
		writer.println(a+" 계산결과 "+ b);
		writer.println("a"+op+"b = "+(result));
		
	}

}
