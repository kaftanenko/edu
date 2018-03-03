package app.arduino.sirenofshame.common.service.host;

import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.readBytes;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.writeBytes;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.common.service.host.util.SerialChannelLogManager;
import dk.thibaut.serial.SerialChannel;

public abstract class AbstractSerialChannelHostController {

	// ... constants

	private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

	// ... dependencies

	protected static final Logger LOG = SerialChannelLogManager.getLogger();

	// ... properties

	protected SerialChannel serialChannel;
	protected String serialChannelPortName;

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
			return readBytes(serialChannel);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	protected static RuntimeException handleFatalException(final Exception ex) {

		throw new RuntimeException(ex);
	}

}
