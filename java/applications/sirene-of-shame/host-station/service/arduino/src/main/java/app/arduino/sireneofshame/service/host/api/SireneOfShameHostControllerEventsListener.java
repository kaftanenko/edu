package app.arduino.sireneofshame.service.host.api;

public interface SireneOfShameHostControllerEventsListener {

	// ... events handler methods

	public void onStateChanged(ESireneOfShameAlarmLevel from, ESireneOfShameAlarmLevel to);

}
