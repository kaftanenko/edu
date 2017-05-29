package app.arduino.sereneofshame.service.api.config;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;

public class DefaultSireneOfShameControllerConfig implements SireneOfShameControllerConfig {

	// ... business methods

	@Override
	public ESireneOfShameState getInitialState() {

		return ESireneOfShameState.GREENBLUE;
	}

}
