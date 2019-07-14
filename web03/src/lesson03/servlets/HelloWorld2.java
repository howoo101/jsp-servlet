package lesson03.servlets;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld2 extends GenericServlet{
	//genericsServlet 클래스는 servlet을 구현한 추상클래스
	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest config, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service!!");
	}
	
}
