package app.arduino.sereneofshame.service.host.api.config;

import app.arduino.sereneofshame.service.host.api.ESireneOfShameState;

public class DefaultSireneOfShameHostControllerConfig implements SireneOfShameHostControllerConfig {

	// ... business methods

	@Override
	public ESireneOfShameState getInitialState() {

		return ESireneOfShameState.GREENBLUE;
	}

}
