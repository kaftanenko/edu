package app.arduino.sereneofshame.service.impl.serial.host;

import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sereneofshame.service.impl.serial.host.util.SerialChannelUtils.findSerialChannelByAndOpenIt;
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

	private static final String COMMAND_SET_STATE_TO_RED = "SET_STATE_TO_RED";
	private static final String COMMAND_SET_STATE_TO_YELLOW = "SET_STATE_TO_YELLOW";
	private static final String COMMAND_SET_STATE_TO__GREENBLUE = "SET_STATE_TO_GREENBLUE";

	private static final String EXPECTED_GREETING_MESSAGE = "Wellcome to the \"Sirene Of Shame\"!";

	// ... properties

	private final SerialChannel channel;

	// ... constructors

	public SireneOfShameSerialHostController() {

		this(new DefaultSireneOfShameControllerConfig());
	}

	public SireneOfShameSerialHostController(final SireneOfShameControllerConfig configuration) {

		super(configuration);

		channel = findSerialChannelByAndOpenIt(EXPECTED_GREETING_MESSAGE);
		setState(configuration.getInitialState());
	}

	@Override
	public void close() throws Exception {

		closeSerialChannel(channel);
	}

	// ... business methods

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
			writeBytes(channel, commandMessage);
		} catch (final Exception ex) {
			throw handleFatalException(ex);
		}
	}

	private String readResponse() {

		try {
			return SerialChannelUtils.readBytes(channel);
		} catch (final IOException ex) {
			throw handleFatalException(ex);
		}
	}

	private static RuntimeException handleFatalException(final Exception ex) {

		throw new RuntimeException(ex);
	}

}
