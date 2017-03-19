package edu.java.fws.log4j;

import org.apache.log4j.Logger;

public class Log4JLauncher {

	// ... constants

	public static Logger logger = Logger.getLogger(Log4JLauncher.class);

	// ... launcher method

	public static void main(final String[] args) {

		logger.error("error-message");
		logger.warn("warn message");
		logger.info("info message");
		logger.debug("debug-message");
		logger.trace("trace-message");
	}

}
