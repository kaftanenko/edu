package osgi.calculator.access.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

	// ... constants

	private static final long serialVersionUID = 1L;

	// ... life cycle methods

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final ServletOutputStream out = resp.getOutputStream();

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>");
		out.println("Hello World!");
		out.println("</h2>");
		out.println("</body>");
		out.println("</html>");
	}

}
