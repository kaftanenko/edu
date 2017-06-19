package app.arduino.sireneofshame.service.host.impl.dummy;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;
import app.arduino.sireneofshame.service.host.api.config.DefaultSireneOfShameHostControllerConfig;
import app.arduino.sireneofshame.service.host.api.config.SireneOfShameHostControllerConfig;
import app.arduino.sireneofshame.service.host.impl.common.AbstractSireneOfShameHostController;

public class DummySireneOfShameHostController extends AbstractSireneOfShameHostController {

	// ... properties

	private ESireneOfShameAlarmLevel currentState;

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
	public ESireneOfShameAlarmLevel getCurrentAlarmLevel() {

		return this.currentState;
	}

	@Override
	public void setAlarmLevelTo(final ESireneOfShameAlarmLevel state) {

		final ESireneOfShameAlarmLevel fromState = this.currentState;
		this.currentState = state;

		notifyEventsListenersAboutStateChange(fromState, currentState);
	}

}
