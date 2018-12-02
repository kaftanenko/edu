package app.arduino.sirenofshame.common.service.host.event;

public interface SerialChannelEventsListener {

  // ... events handler methods

  public void onException(Throwable throwable);

  // ...
  
  public void onConnectedTo(String portName);

  public void onDisconnectedFrom(String portName);
  
  // ...

  public void onDataSent(byte[] data);
  
  public void onDataReceived(byte[] data);

  // ...

  public void onMessageSent(String message);

  public void onMessageReceived(String message);

}
