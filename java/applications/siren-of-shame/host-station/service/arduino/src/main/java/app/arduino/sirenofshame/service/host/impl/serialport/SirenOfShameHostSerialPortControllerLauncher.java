package app.arduino.sirenofshame.service.host.impl.serialport;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;

public class SirenOfShameHostSerialPortControllerLauncher {

	// ... launcher methods

	public static void main(final String[] args) {

		try (SirenOfShameHostSerialPortController controller = new SirenOfShameHostSerialPortController()) {
			controller.connect();
			controller.setAlarmLevelTo(ESirenOfShameAlarmLevel.RED);
			Thread.sleep(10000);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
