package app.arduino.sireneofshame.service.host.api.config;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;

public class DefaultSireneOfShameHostControllerConfig implements SireneOfShameHostControllerConfig {

	// ... business methods

	@Override
	public ESireneOfShameAlarmLevel getInitialState() {

		return ESireneOfShameAlarmLevel.GREENBLUE;
	}

}
