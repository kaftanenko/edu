package osgi.calculator.access.http.internal;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import osgi.calculator.access.http.HelloWorldServlet;
import osgi.calculator.common.OsgiBundleActivator;

public class HttpBundleActivator extends OsgiBundleActivator {

	// ... constants

	private static final String SERVICE_WEB_CONTEXT_TOKEN = "/service";

	// ... properties

	private HttpService httpService = null;

	// ... life cycle methods

	@Override
	public void start(final BundleContext bc) throws Exception {

		super.start(bc, new Runnable() {

			@Override
			public void run() {

				final ServiceReference httpServiceReference = bc.getServiceReference(HttpService.class.getName());
				if (httpServiceReference != null) {
					httpService = (HttpService) bc.getService(httpServiceReference);
					log("... HttpService weaved successfully!");
				} else {
					log("... HttpService couldn't be weaved!");
				}

				final Hashtable<String, Object> initparams = new Hashtable<String, Object>();
				final HttpContext httpContext = httpService.createDefaultHttpContext();

				try {
					httpService.registerServlet(SERVICE_WEB_CONTEXT_TOKEN, new HelloWorldServlet(), initparams,
							httpContext);
				} catch (ServletException ex) {
					handleServiceRegistrationException(ex);
				} catch (NamespaceException ex) {
					handleServiceRegistrationException(ex);
				}
			}

			private void handleServiceRegistrationException(Exception ex) {
				throw new RuntimeException(ex);
			}
		});
	}

}