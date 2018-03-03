package app.arduino.sirenofshame.service.host.impl.common;

import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.writeBytes;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.service.host.impl.SirenOfShameHostFactory;
import app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils;
import dk.thibaut.serial.SerialChannel;

public abstract class AbstractSirenOfShameCommonHostController {

	// ... constants

	private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

	// ... dependencies

	protected static final Logger LOG = SirenOfShameHostFactory.getLogger();

	// ... properties

	protected SerialChannel serialChannel;
	private String serialChannelPortName;

	// ... business methods

	public void connect() {

		try {

			serialChannelPortName = findSerialChannelByWellcomeMessage(EXPECTING_WELCOME_MESSAGE);
			serialChannel = openSerialChannel(serialChannelPortName);
			readResponse(); // ... read welcome message
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
	}

	public void disconnect() {

		try {

			closeSerialChannel(serialChannel);
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
	}

	public String getPortName() {

		return serialChannelPortName;
	}

	// ... helper methods

	protected void sendCommand(final String commandMessage) {

		try {
			writeBytes(serialChannel, commandMessage);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	protected String readResponse() {

		try {
			return SerialChannelUtils.readBytes(serialChannel);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	protected static RuntimeException handleFatalException(final Exception ex) {

		throw new RuntimeException(ex);
	}

}
