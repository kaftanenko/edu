package edu.java.spring.rest.access.stateless.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import edu.java.spring.rest.access.stateless.util.LogUtils;

public class GreetingServiceRestFilter extends GenericFilterBean {

	// ... filter methods

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		LogUtils.logEntryPoint();

		chain.doFilter(request, response);

		LogUtils.logExitPoint();
	}

	// ... helper methods
}