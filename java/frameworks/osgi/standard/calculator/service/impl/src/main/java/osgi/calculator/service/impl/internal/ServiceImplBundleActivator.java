package osgi.calculator.service.impl.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import osgi.calculator.common.OsgiBundleActivator;
import osgi.calculator.service.api.ISimpleCalculatorService;
import osgi.calculator.service.impl.SimpleCalculatorService;

public class ServiceImplBundleActivator extends OsgiBundleActivator {

	// ... properties

	final Object calcService = new SimpleCalculatorService();

	// ... life cycle methods

	@Override
	public void start(final BundleContext bc) throws Exception {

		super.start(bc, new Runnable() {

			@Override
			public void run() {

				final Dictionary<String, Object> properties = new Hashtable<String, Object>();
				final ServiceRegistration calcServiceReg = bc.registerService(
						ISimpleCalculatorService.class.getName(), calcService, properties);

				if (calcServiceReg == null) {
					log("... ISimpleCalculationService couldn't be registered!");
				} else {
					log("... ISimpleCalculationService successfully registered!");
				}
			}
		});
	}

	// ... helper methods

}
