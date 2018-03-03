package app.arduino.sirenofshame.singlestate.service.host.api;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;

public interface SirenOfShameSingleStateHostController {

	// ... business methods

	public ESirenOfShameAlarmLevel getCurrentAlarmLevel();

	void setAlarmLevelTo(ESirenOfShameAlarmLevel alarmLevel);

	// ... events management methods

	public void subscribe(SirenOfShameSingleStateHostControllerEventsListener eventsListener);

	public void unsubscribe(SirenOfShameSingleStateHostControllerEventsListener eventsListener);

}
