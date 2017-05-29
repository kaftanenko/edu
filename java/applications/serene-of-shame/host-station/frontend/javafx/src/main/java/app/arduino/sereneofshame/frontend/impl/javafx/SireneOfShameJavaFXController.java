package app.arduino.sereneofshame.frontend.impl.javafx;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameControllerEventsListener;
import app.arduino.sereneofshame.service.impl.serial.host.SireneOfShameSerialHostController;

public class SireneOfShameJavaFXController {

	// ... properties

	private final SireneOfShameJavaFXApplication gui;
	private final SireneOfShameSerialHostController sireneOfShameController;

	// ... constructors

	public SireneOfShameJavaFXController(final SireneOfShameJavaFXApplication gui) {

		this.gui = gui;

		sireneOfShameController = new SireneOfShameSerialHostController();
		final SireneOfShameControllerEventsListener eventsListener = new SireneOfShameControllerEventsListener() {
			@Override
			public void onStateChanged(final ESireneOfShameState from, final ESireneOfShameState to) {
				gui.onStateChanged(to);
			}
		};
		sireneOfShameController.subscribe(eventsListener);
	}

	public void connect() {

		sireneOfShameController.connect();

		final String portName = sireneOfShameController.getPortName();
		final ESireneOfShameState state = sireneOfShameController.getState();

		gui.onConnected(portName, state);

	}

	public void disconnect() {

		sireneOfShameController.disconnect();

		gui.onDisconnected();
	}

	public void setState(final ESireneOfShameState state) {

		sireneOfShameController.setState(state);

		gui.onStateChanged(state);
	}

}
