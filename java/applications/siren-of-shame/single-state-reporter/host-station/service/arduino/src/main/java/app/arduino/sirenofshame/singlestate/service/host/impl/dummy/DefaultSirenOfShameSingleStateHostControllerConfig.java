package app.arduino.sirenofshame.singlestate.service.host.impl.dummy;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.api.type.SirenOfShameSingleStateHostControllerConfig;

public class DefaultSirenOfShameSingleStateHostControllerConfig implements SirenOfShameSingleStateHostControllerConfig {

	// ... business methods

	@Override
	public ESirenOfShameAlarmLevel getInitialState() {

		return ESirenOfShameAlarmLevel.GREENBLUE;
	}

}
