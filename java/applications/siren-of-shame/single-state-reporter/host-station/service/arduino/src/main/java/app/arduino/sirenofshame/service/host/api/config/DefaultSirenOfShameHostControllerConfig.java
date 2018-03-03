package app.arduino.sirenofshame.service.host.api.config;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;

public class DefaultSirenOfShameHostControllerConfig implements SirenOfShameHostControllerConfig {

	// ... business methods

	@Override
	public ESirenOfShameAlarmLevel getInitialState() {

		return ESirenOfShameAlarmLevel.GREENBLUE;
	}

}
