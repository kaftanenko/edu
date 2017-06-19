package app.arduino.sireneofshame.service.host.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SireneOfShameHostFactory {

	// ... dependencies

	private static final Logger SIRENE_OF_SHAME_HOST_LOG = LogManager.getLogger("SIRENE-OF-SHAME_HOST");

	private static final Logger SERIAL_PORT_CHANNEL_LOG = LogManager.getLogger("SERIAL_PORT_CHANNEL");

	// ... business methods

	public static Logger getLogger() {

		return SIRENE_OF_SHAME_HOST_LOG;
	}

	public static Logger getSerialPortChannelLogger() {

		return SERIAL_PORT_CHANNEL_LOG;
	}

}
