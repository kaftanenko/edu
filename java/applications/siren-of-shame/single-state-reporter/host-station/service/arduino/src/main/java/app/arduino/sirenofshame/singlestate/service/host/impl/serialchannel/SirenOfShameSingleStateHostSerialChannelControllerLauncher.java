package app.arduino.sirenofshame.singlestate.service.host.impl.serialchannel;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;

public class SirenOfShameSingleStateHostSerialChannelControllerLauncher {

	// ... launcher methods

	public static void main(final String[] args) {

		try (SirenOfShameSingleStateHostSerialChannelController controller = new SirenOfShameSingleStateHostSerialChannelController()) {
			controller.connect();
			controller.setAlarmLevelTo(ESirenOfShameAlarmLevel.RED);
			Thread.sleep(10000);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
