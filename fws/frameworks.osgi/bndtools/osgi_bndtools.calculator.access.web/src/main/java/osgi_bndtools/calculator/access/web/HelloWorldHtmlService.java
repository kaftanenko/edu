package osgi_bndtools.calculator.access.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloWorldHtmlService {

	@GET
	@Produces(MediaType.APPLICATION_XHTML_XML)
	public Response getDataToDisplay() {

		return Response.ok().entity("Yes, it works.").build();

		// final StringBuilder sb = new StringBuilder();
		//
		// sb.append("<html>");
		// sb.append("<body>");
		// sb.append("<h2>");
		// sb.append("Hello World!");
		// sb.append("</h2>");
		// sb.append("</body>");
		// sb.append("</html>");
		//
		// return sb.toString();
	}

}
