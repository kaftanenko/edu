package app.arduino.sirenofshame.service.host.api;

public interface SirenOfShameHostControllerEventsListener {

	// ... events handler methods

	public void onStateChanged(ESirenOfShameAlarmLevel from, ESirenOfShameAlarmLevel to);

}
