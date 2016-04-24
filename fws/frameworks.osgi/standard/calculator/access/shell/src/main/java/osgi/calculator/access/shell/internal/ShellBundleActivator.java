package osgi.calculator.access.shell.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import osgi.calculator.common.OsgiBundleActivator;
import osgi.calculator.service.api.ISimpleCalculatorService;

public class ShellBundleActivator extends OsgiBundleActivator {

	// ... constants

	public static final String CALC_SHELL_COMMANDS_SCOPE = "calc";
	public static final String[] CALC_SHELL_COMMANDS = new String[] { "add", "div", "mul", "sub" };

	// ... properties

	protected Object calcService;

	// ... life cycle methods

	@Override
	public void start(final BundleContext bc) throws Exception {

		super.start(bc, new Runnable() {

			@Override
			public void run() {

				final ServiceReference calcServiceRef = bc.getServiceReference(ISimpleCalculatorService.class
						.getName());
				if (calcServiceRef != null) {

					calcService = bc.getService(calcServiceRef);

					final Dictionary<String, Object> serviceProps = new Hashtable<String, Object>();
					serviceProps.put("osgi.command.scope", CALC_SHELL_COMMANDS_SCOPE);
					serviceProps.put("osgi.command.function", CALC_SHELL_COMMANDS);

					bc.registerService(calcService.getClass().getName(), calcService, serviceProps);

					log(" ... ISimpleCalculationService  weaved successfully!");
				} else {
					log(" ... ISimpleCalculationService  couldn't be weaved!");
				}
			}
		});

	}

}