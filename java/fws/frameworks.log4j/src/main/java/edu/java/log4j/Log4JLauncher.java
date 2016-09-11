package edu.java.log4j;

import org.apache.log4j.Logger;

public class Log4JLauncher {

	public static Logger logger = Logger.getLogger(Log4JLauncher.class);

	public static void main(String[] args) {

		logger.debug("abc");
	}

}
