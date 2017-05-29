package app.arduino.sereneofshame.service.impl.serial.host;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;

public class SireneOfShameSerialHostControllerLauncher {

	// ... launcher methods

	public static void main(final String[] args) {

		try (SireneOfShameSerialHostController controller = new SireneOfShameSerialHostController()) {
			controller.open();
			controller.setState(ESireneOfShameState.RED);
			Thread.sleep(10000);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
