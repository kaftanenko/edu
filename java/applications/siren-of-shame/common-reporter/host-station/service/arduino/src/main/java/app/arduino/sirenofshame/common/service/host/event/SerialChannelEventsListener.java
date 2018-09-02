package app.arduino.sirenofshame.common.service.host.event;

public interface SerialChannelEventsListener {

  // ... events handler methods

  public void onDataReceived(byte[] data);

  public void onMessageReceived(String message);

  public void onDataSent(byte[] data);

  public void onMessageSent(String message);

}
