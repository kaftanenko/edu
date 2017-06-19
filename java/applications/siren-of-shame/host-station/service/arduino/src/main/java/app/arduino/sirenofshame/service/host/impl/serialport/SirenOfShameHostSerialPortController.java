package app.arduino.sirenofshame.service.host.impl.serialport;

import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils.writeBytes;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.service.host.api.config.DefaultSirenOfShameHostControllerConfig;
import app.arduino.sirenofshame.service.host.api.config.SirenOfShameHostControllerConfig;
import app.arduino.sirenofshame.service.host.impl.SirenOfShameHostFactory;
import app.arduino.sirenofshame.service.host.impl.common.AbstractSirenOfShameHostController;
import app.arduino.sirenofshame.service.host.impl.serialport.util.SerialChannelUtils;
import dk.thibaut.serial.SerialChannel;

public class SirenOfShameHostSerialPortController extends AbstractSirenOfShameHostController
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

	private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

	private static final Logger LOG = SirenOfShameHostFactory.getLogger();

	// ... properties

	private SerialChannel serialChannel;
	private String serialChannelPortName;

	// ... constructors

	public SirenOfShameHostSerialPortController() {

		this(new DefaultSirenOfShameHostControllerConfig());
	}

	public SirenOfShameHostSerialPortController(final SirenOfShameHostControllerConfig configuration) {

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
	public ESirenOfShameAlarmLevel getCurrentAlarmLevel() {

		try {
			sendCommand(COMMAND__GET_CURRENT_ALARM_LEVEL);

			final String currentState = readResponse();
			return ESirenOfShameAlarmLevel.valueOf(currentState);
		} catch (final Exception ex) {
			LOG.error(ex.getMessage());
			return ESirenOfShameAlarmLevel.UNDEFINED;
		}
	}

	@Override
	public void setAlarmLevelTo(final ESirenOfShameAlarmLevel alarmLevel) {

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

		final ESirenOfShameAlarmLevel from = getCurrentAlarmLevel();

		sendCommand(commandMessage);
		readResponse();

		final ESirenOfShameAlarmLevel to = getCurrentAlarmLevel();

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
