package app.arduino.sereneofshame.service.impl.dummy;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.config.DefaultSireneOfShameControllerConfig;
import app.arduino.sereneofshame.service.api.config.SireneOfShameControllerConfig;
import app.arduino.sereneofshame.service.impl.common.AbstractSireneOfShameController;

public class DummySireneOfShameController extends AbstractSireneOfShameController {

	// ... properties

	private ESireneOfShameState currentState;

	// ... constructors

	public DummySireneOfShameController() {

		this(new DefaultSireneOfShameControllerConfig());
	}

	public DummySireneOfShameController(final SireneOfShameControllerConfig configuration) {

		super(configuration);
		this.currentState = this.configuration.getInitialState();
	}

	// ... business methods

	@Override
	public ESireneOfShameState getState() {

		return this.currentState;
	}

	@Override
	public void setState(final ESireneOfShameState state) {

		final ESireneOfShameState fromState = this.currentState;
		this.currentState = state;

		notifyEventsListenersAboutStateChange(fromState, currentState);
	}

}
