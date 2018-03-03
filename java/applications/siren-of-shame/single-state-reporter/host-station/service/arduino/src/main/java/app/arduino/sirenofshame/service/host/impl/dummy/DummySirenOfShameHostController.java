package app.arduino.sirenofshame.service.host.impl.dummy;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.service.host.api.config.DefaultSirenOfShameHostControllerConfig;
import app.arduino.sirenofshame.service.host.api.config.SirenOfShameHostControllerConfig;
import app.arduino.sirenofshame.service.host.impl.common.AbstractSirenOfShameHostController;

public class DummySirenOfShameHostController extends AbstractSirenOfShameHostController {

	// ... properties

	private ESirenOfShameAlarmLevel currentState;

	// ... constructors

	public DummySirenOfShameHostController() {

		this(new DefaultSirenOfShameHostControllerConfig());
	}

	public DummySirenOfShameHostController(final SirenOfShameHostControllerConfig configuration) {

		super(configuration);
		this.currentState = this.configuration.getInitialState();
	}

	// ... business methods

	@Override
	public ESirenOfShameAlarmLevel getCurrentAlarmLevel() {

		return this.currentState;
	}

	@Override
	public void setAlarmLevelTo(final ESirenOfShameAlarmLevel state) {

		final ESirenOfShameAlarmLevel fromState = this.currentState;
		this.currentState = state;

		notifyEventsListenersAboutStateChange(fromState, currentState);
	}

}
