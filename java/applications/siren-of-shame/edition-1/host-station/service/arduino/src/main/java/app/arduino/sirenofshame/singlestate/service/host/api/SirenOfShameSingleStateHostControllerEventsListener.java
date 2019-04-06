package app.arduino.sirenofshame.singlestate.service.host.api;

import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;

public interface SirenOfShameSingleStateHostControllerEventsListener {

  // ... events handler methods

  public void onStateChanged(ESirenOfShameAlarmLevel from, ESirenOfShameAlarmLevel to);

}
