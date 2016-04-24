package osgi_bndtools.calculator.access.http;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aQute.bnd.annotation.component.Component;

@SuppressWarnings("serial")
@Component(provide=Servlet.class, properties = { "alias=/hello" })
public class HelloWorldHtmlService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
