package app.arduino.sirenofshame.singlestate.service.host.impl.dummy;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.api.type.SirenOfShameSingleStateHostControllerConfig;
import app.arduino.sirenofshame.singlestate.service.host.impl.common.AbstractSirenOfShameHostController;

public class DummySirenOfShameSingleStateHostController extends AbstractSirenOfShameHostController {

	// ... properties

	private ESirenOfShameAlarmLevel currentState;

	// ... constructors

	public DummySirenOfShameSingleStateHostController() {

		this(new DefaultSirenOfShameSingleStateHostControllerConfig());
	}

	public DummySirenOfShameSingleStateHostController(final SirenOfShameSingleStateHostControllerConfig configuration) {

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
