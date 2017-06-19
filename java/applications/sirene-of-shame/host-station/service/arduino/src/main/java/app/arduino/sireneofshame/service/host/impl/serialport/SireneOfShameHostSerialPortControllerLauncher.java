package app.arduino.sireneofshame.service.host.impl.serialport;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;

public class SireneOfShameHostSerialPortControllerLauncher {

	// ... launcher methods

	public static void main(final String[] args) {

		try (SireneOfShameHostSerialPortController controller = new SireneOfShameHostSerialPortController()) {
			controller.connect();
			controller.setAlarmLevelTo(ESireneOfShameAlarmLevel.RED);
			Thread.sleep(10000);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
