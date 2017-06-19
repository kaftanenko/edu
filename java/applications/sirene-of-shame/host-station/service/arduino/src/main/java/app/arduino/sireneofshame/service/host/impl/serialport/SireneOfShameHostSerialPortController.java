package app.arduino.sireneofshame.service.host.impl.serialport;

import static app.arduino.sireneofshame.service.host.impl.serialport.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sireneofshame.service.host.impl.serialport.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sireneofshame.service.host.impl.serialport.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sireneofshame.service.host.impl.serialport.util.SerialChannelUtils.writeBytes;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;
import app.arduino.sireneofshame.service.host.api.config.DefaultSireneOfShameHostControllerConfig;
import app.arduino.sireneofshame.service.host.api.config.SireneOfShameHostControllerConfig;
import app.arduino.sireneofshame.service.host.impl.SireneOfShameHostFactory;
import app.arduino.sireneofshame.service.host.impl.common.AbstractSireneOfShameHostController;
import app.arduino.sireneofshame.service.host.impl.serialport.util.SerialChannelUtils;
import dk.thibaut.serial.SerialChannel;

public class SireneOfShameHostSerialPortController extends AbstractSireneOfShameHostController
		implements AutoCloseable {

	// ... constants

	private static final String COMMAND__GET_CURRENT_ALARM_LEVEL = "GET_CURRENT_ALARM_LEVEL";

	private static final String COMMAND__PING = "PING";
	private static final String COMMAND__PING_RESPONSE_SUCCEEDED = "SUCCEEDED";

	private static final String COMMAND__SET_ALARM_LEVEL_TO__RED = "SET_ALARM_LEVEL_TO RED";
	private static final String COMMAND__SET_ALARM_LEVEL_TO__RED_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO RED_EXPECTING_UPDATE";

	private static final String COMMAND__SET_ALARM_LEVEL_TO__YELLOW = "SET_ALARM_LEVEL_TO YELLOW";
	private static final String COMMAND__SET_ALARM_LEVEL_TO__YELLOW_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO YELLOW_EXPECTING_UPDATE";

	private static final String COMMAND__SET_ALARM_LEVEL_TO__GREENBLUE = "SET_ALARM_LEVEL_TO GREENBLUE";
	private static final String COMMAND__SET_ALARM_LEVEL_TO__GREENBLUE_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO GREENBLUE_EXPECTING_UPDATE";

	private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Sirene Of Shame\"!";

	private static final Logger LOG = SireneOfShameHostFactory.getLogger();

	// ... properties

	private SerialChannel serialChannel;
	private String serialChannelPortName;

	// ... constructors

	public SireneOfShameHostSerialPortController() {

		this(new DefaultSireneOfShameHostControllerConfig());
	}

	public SireneOfShameHostSerialPortController(final SireneOfShameHostControllerConfig configuration) {

		super(configuration);
	}

	@Override
	public void close() throws Exception {

		disconnect();
	}

	// ... business methods

	public void connect() {

		try {

			serialChannelPortName = findSerialChannelByWellcomeMessage(EXPECTING_WELCOME_MESSAGE);
			serialChannel = openSerialChannel(serialChannelPortName);
			readResponse(); // ... read welcome message

			setAlarmLevelTo(configuration.getInitialState());
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

	public boolean isConnected() throws Exception {

		try {
			if (serialChannel != null && serialChannel.isOpen()) {

				sendCommand(COMMAND__PING);
				final String responseMessage = readResponse();
				return COMMAND__PING_RESPONSE_SUCCEEDED.equals(responseMessage);
			} else {
				return false;
			}
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
	}

	@Override
	public ESireneOfShameAlarmLevel getCurrentAlarmLevel() {

		try {
			sendCommand(COMMAND__GET_CURRENT_ALARM_LEVEL);

			final String currentState = readResponse();
			return ESireneOfShameAlarmLevel.valueOf(currentState);
		} catch (final Exception ex) {
			LOG.error(ex.getMessage());
			return ESireneOfShameAlarmLevel.UNDEFINED;
		}
	}

	@Override
	public void setAlarmLevelTo(final ESireneOfShameAlarmLevel alarmLevel) {

		final String commandMessage;

		switch (alarmLevel) {
			case RED:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__RED;
				break;
			case RED_EXPECTING_UPDATE:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__RED_EXPECTING_UPDATE;
				break;
			case YELLOW:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__YELLOW;
				break;
			case YELLOW_EXPECTING_UPDATE:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__YELLOW_EXPECTING_UPDATE;
				break;
			case GREENBLUE:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__GREENBLUE;
				break;
			case GREENBLUE_EXPECTING_UPDATE:
				commandMessage = COMMAND__SET_ALARM_LEVEL_TO__GREENBLUE_EXPECTING_UPDATE;
				break;
			default:
				throw new RuntimeException("Unsupported alarm level: " + alarmLevel);
		}

		final ESireneOfShameAlarmLevel from = getCurrentAlarmLevel();

		sendCommand(commandMessage);
		readResponse();

		final ESireneOfShameAlarmLevel to = getCurrentAlarmLevel();

		notifyEventsListenersAboutStateChange(from, to);
	}

	// ... helper methods

	private void sendCommand(final String commandMessage) {

		try {
			writeBytes(serialChannel, commandMessage);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	private String readResponse() {

		try {
			return SerialChannelUtils.readBytes(serialChannel);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	private static RuntimeException handleFatalException(final Exception ex) {

		throw new RuntimeException(ex);
	}

}
