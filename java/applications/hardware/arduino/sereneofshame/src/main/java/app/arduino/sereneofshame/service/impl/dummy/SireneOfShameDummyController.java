package app.arduino.sereneofshame.service.impl.dummy;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameController;
import app.arduino.sereneofshame.service.api.config.DefaultSireneOfShameControllerConfig;
import app.arduino.sereneofshame.service.api.config.SireneOfShameControllerConfig;

public class SireneOfShameDummyController implements SireneOfShameController {

	// ... properties

	private ESireneOfShameState currentState;

	private final SireneOfShameControllerConfig configuration;

	// ... constructors

	public SireneOfShameDummyController() {

		this(new DefaultSireneOfShameControllerConfig());
	}

	public SireneOfShameDummyController(final SireneOfShameControllerConfig configuration) {

		this.configuration = configuration;

		this.currentState = this.configuration.getInitialState();
	}

	// ... business methods

	@Override
	public ESireneOfShameState getState() {

		return this.currentState;
	}

	@Override
	public void setState(final ESireneOfShameState state) {

		this.currentState = state;
	}

}
