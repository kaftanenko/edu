package app.arduino.sereneofshame.service.host.api;

public interface SireneOfShameHostController {

	// ... business methods

	public ESireneOfShameAlarmLevel getCurrentAlarmLevel();

	void setAlarmLevelTo(ESireneOfShameAlarmLevel alarmLevel);

	// ... events management methods

	public void subscribe(SireneOfShameHostControllerEventsListener eventsListener);

	public void unsubscribe(SireneOfShameHostControllerEventsListener eventsListener);

}
