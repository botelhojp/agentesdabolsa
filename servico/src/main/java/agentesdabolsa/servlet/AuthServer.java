package agentesdabolsa.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/api")
public class AuthServer implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init");
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		final Enumeration<String> attributeNames = servletRequest.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            System.out.println("{attribute} " + servletRequest.getParameter(attributeNames.nextElement()));
        }

        final Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println("{parameter} " + servletRequest.getParameter(parameterNames.nextElement()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

}
