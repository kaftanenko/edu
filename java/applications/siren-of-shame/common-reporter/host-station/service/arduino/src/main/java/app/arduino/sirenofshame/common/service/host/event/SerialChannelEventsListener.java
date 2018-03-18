package app.arduino.sirenofshame.common.service.host.event;

public interface SerialChannelEventsListener {

  // ... events handler methods

  public void onMessageSent(String message);

  public void onMessageReceived(String message);

}
