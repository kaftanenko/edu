package app.arduino.sereneofshame.service.host.impl.dummy;

import app.arduino.sereneofshame.service.host.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.host.api.config.DefaultSireneOfShameHostControllerConfig;
import app.arduino.sereneofshame.service.host.api.config.SireneOfShameHostControllerConfig;
import app.arduino.sereneofshame.service.host.impl.common.AbstractSireneOfShameHostController;

public class DummySireneOfShameHostController extends AbstractSireneOfShameHostController {

	// ... properties

	private ESireneOfShameState currentState;

	// ... constructors

	public DummySireneOfShameHostController() {

		this(new DefaultSireneOfShameHostControllerConfig());
	}

	public DummySireneOfShameHostController(final SireneOfShameHostControllerConfig configuration) {

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
