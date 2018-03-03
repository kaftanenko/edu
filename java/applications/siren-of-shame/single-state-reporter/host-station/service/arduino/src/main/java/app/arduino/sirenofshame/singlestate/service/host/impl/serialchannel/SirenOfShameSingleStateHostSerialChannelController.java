package app.arduino.sirenofshame.singlestate.service.host.impl.serialchannel;

import java.util.Collection;
import java.util.Map;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.api.type.SirenOfShameSingleStateHostControllerConfig;
import app.arduino.sirenofshame.singlestate.service.host.impl.common.AbstractSirenOfShameHostController;
import app.arduino.sirenofshame.singlestate.service.host.impl.dummy.DefaultSirenOfShameSingleStateHostControllerConfig;

public class SirenOfShameSingleStateHostSerialChannelController
		extends AbstractSirenOfShameHostController //
		implements AutoCloseable //
{

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

	// ... constructors

	public SirenOfShameSingleStateHostSerialChannelController() {

		this(new DefaultSirenOfShameSingleStateHostControllerConfig());
	}

	public SirenOfShameSingleStateHostSerialChannelController(final SirenOfShameSingleStateHostControllerConfig configuration) {

		super(configuration);
	}

	@Override
	public void close() throws Exception {

		disconnect();
	}

	// ... business methods

	@Override
	public void connect() {

		try {
			super.connect();
			setAlarmLevelTo(configuration.getInitialState());
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
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

	public void updateJenkinsStateInfo(final Collection<Map<String, Object>> jsonJobNodes) {

	}

}
