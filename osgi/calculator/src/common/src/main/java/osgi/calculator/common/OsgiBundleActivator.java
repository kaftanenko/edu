package osgi.calculator.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class OsgiBundleActivator implements BundleActivator {

	// ... constants

	protected final String BUNDLE_LOG_MSG_PREFIX = "[" + this.getClass().getSimpleName() + "] ";

	// ... properties

	private LogService log = null;

	// ... life cycle methods

	@Override
	public void start(BundleContext bc) throws Exception {

		start(bc, null);
	}

	protected void start(BundleContext bc, Runnable customInitCode) {

		final ServiceReference logServiceReference = bc.getServiceReference(LogService.class.getName());
		if (logServiceReference != null) {
			log = (LogService) bc.getService(logServiceReference);
			log("... LogService weaved successfully!");
		} else {
			log("... LogService couldn't be weaved!");
		}

		if (customInitCode != null) {
			final Thread customInitThread = new Thread(customInitCode);
			customInitThread.run();
		}

		log("... bundle is started!");
	}

	@Override
	public void stop(BundleContext bc) throws Exception {

		log("... bundle is stopped!");
	}

	// ... helper methods

	protected void log(String message) {

		final String logMessage = BUNDLE_LOG_MSG_PREFIX + message;

		if (log != null) {
			log.log(LogService.LOG_INFO, logMessage);
		}
		System.out.println(logMessage);
	}
}
