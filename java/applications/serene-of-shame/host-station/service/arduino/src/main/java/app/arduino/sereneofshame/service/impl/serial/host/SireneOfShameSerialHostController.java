package app.arduino.sereneofshame.service.impl.serial.host;

import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.writeBytes;

import java.io.IOException;

import org.assertj.core.api.Assertions;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.config.DefaultSireneOfShameControllerConfig;
import app.arduino.sereneofshame.service.api.config.SireneOfShameControllerConfig;
import app.arduino.sereneofshame.service.impl.common.AbstractSireneOfShameController;
import app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils;
import dk.thibaut.serial.SerialChannel;

public class SireneOfShameSerialHostController extends AbstractSireneOfShameController implements AutoCloseable {

	// ... constants

	private static final String COMMAND_GET_STATE = "GET_STATE";

	private static final String COMMAND_PING = "PING";
	private static final String COMMAND_PING_RESPONSE_SUCCEEDED = "SUCCEEDED";

	private static final String COMMAND_SET_STATE_TO_RED = "SET_STATE_TO_RED";
	private static final String COMMAND_SET_STATE_TO_YELLOW = "SET_STATE_TO_YELLOW";
	private static final String COMMAND_SET_STATE_TO__GREENBLUE = "SET_STATE_TO_GREENBLUE";

	private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Sirene Of Shame\"!";

	// ... properties

	private SerialChannel serialChannel;
	private String serialChannelPortName;

	// ... constructors

	public SireneOfShameSerialHostController() {

		this(new DefaultSireneOfShameControllerConfig());
	}

	public SireneOfShameSerialHostController(final SireneOfShameControllerConfig configuration) {

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

			setState(configuration.getInitialState());
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

				sendCommand(COMMAND_PING);
				final String responseMessage = readResponse();
				return COMMAND_PING_RESPONSE_SUCCEEDED.equals(responseMessage);
			} else {
				return false;
			}
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
	}

	@Override
	public ESireneOfShameState getState() {

		sendCommand(COMMAND_GET_STATE);

		final String currentState = readResponse();
		return ESireneOfShameState.valueOf(currentState);
	}

	@Override
	public void setState(final ESireneOfShameState state) {

		final String commandMessage;

		switch (state) {
			case RED:
				commandMessage = COMMAND_SET_STATE_TO_RED;
				break;
			case YELLOW:
				commandMessage = COMMAND_SET_STATE_TO_YELLOW;
				break;
			case GREENBLUE:
				commandMessage = COMMAND_SET_STATE_TO__GREENBLUE;
				break;
			default:
				throw new RuntimeException("Unsupported state: " + state);
		}

		final ESireneOfShameState from = getState();

		sendCommand(commandMessage);
		readResponse();

		final ESireneOfShameState to = getState();
		Assertions.assertThat(to).isEqualTo(state);

		notifyEventsListenersAboutStateChange(from, to);
	}

	// ... helper methods

	private void sendCommand(final String commandMessage) {

		try {
			writeBytes(serialChannel, commandMessage);
		} catch (final Exception ex) {
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
