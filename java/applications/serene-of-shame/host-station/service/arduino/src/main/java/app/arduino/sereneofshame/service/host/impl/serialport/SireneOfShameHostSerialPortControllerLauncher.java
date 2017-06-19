package app.arduino.sereneofshame.service.host.impl.serialport;

import app.arduino.sereneofshame.service.host.api.ESireneOfShameState;

public class SireneOfShameHostSerialPortControllerLauncher {

	// ... launcher methods

	public static void main(final String[] args) {

		try (SireneOfShameHostSerialPortController controller = new SireneOfShameHostSerialPortController()) {
			controller.connect();
			controller.setState(ESireneOfShameState.RED);
			Thread.sleep(10000);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
