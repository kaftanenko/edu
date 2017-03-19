package edu.java.spring.rest.access.stateless.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	// ... constants

	private static final String CURRENT_PACKAGE_NAME = LogUtils.class.getPackage().getName();

	private static final String DEFAULT_COMPONENT_NAME__UNDEFINED = "UNDEFINED COMPONENT";

	// ... business methods

	public static String logEntryPoint() {

		final String componentName = getComponentName();
		getLogger().info("=> " + componentName + " entered...");
		return componentName;
	}

	public static void logExitPoint() {

		final String componentName = getComponentName();
		getLogger().info("<= ... " + componentName + " left.");
	}

	public static void logInputParameters(String... parameters) {

		final String componentName = getComponentName();
		getLogger().info("=> " + componentName + " entered with the following input parameters: " + parameters);
	}

	public static void logOutputResult(Object result) {

		final String componentName = getComponentName();
		getLogger().info("<= ... " + componentName + " left with the following result: " + result + ".");
	}

	// ... helper methods

	public static String getComponentName() {

		try {
			throw new RuntimeException();
		} catch (RuntimeException ex) {
			for (StackTraceElement stackTraceElement : ex.getStackTrace()) {

				final String componentName = stackTraceElement.getClassName();
				if (!componentName.startsWith(CURRENT_PACKAGE_NAME)) {
					return componentName;
				}
			}
		}
		return DEFAULT_COMPONENT_NAME__UNDEFINED;
	}

	private static Logger getLogger() {

		final String componentName = getComponentName();
		final Logger logger = LoggerFactory.getLogger(componentName);

		return logger;
	}

}
