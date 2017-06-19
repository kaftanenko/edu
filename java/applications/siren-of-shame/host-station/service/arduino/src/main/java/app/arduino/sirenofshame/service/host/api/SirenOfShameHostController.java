package app.arduino.sirenofshame.service.host.api;

public interface SirenOfShameHostController {

	// ... business methods

	public ESirenOfShameAlarmLevel getCurrentAlarmLevel();

	void setAlarmLevelTo(ESirenOfShameAlarmLevel alarmLevel);

	// ... events management methods

	public void subscribe(SirenOfShameHostControllerEventsListener eventsListener);

	public void unsubscribe(SirenOfShameHostControllerEventsListener eventsListener);

}
