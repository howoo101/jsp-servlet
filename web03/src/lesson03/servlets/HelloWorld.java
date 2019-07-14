package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet{
	
	ServletConfig config;
	//준비된 자원 해제코드들 
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	@Override
	public ServletConfig getServletConfig() {
		System.out.println("");
		return config;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return "HelloWorld Servlet";
	}
	
	//서비스하는데 필요한 자원 준비?
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
		this.config = config;
	}

	@Override
	public void service(ServletRequest config, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service");
	}
	
}
