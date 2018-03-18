package app.arduino.sirenofshame.common.service.host;

import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.detectSerialPortName;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.readData;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.readMessage;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.writeData;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.writeMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.common.service.host.event.SerialChannelEventsListener;
import app.arduino.sirenofshame.common.service.host.event.SerialChannelEventsListenerRunnable;
import dk.thibaut.serial.SerialChannel;

public class SerialChannelHostController {

  // ... constants

  private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

  // ... dependencies

  protected final Logger LOG = LogManager.getLogger(this);

  // ... properties

  protected SerialChannel connectedToSerialChannel;
  private String connectedToSerialChannelPortName;

  private final boolean useEventsListeningThread;
  private boolean keepEventsListeningThreadRunning;
  private final List<SerialChannelEventsListener> eventsListeners = new ArrayList<>();

  // ... constructors

  public SerialChannelHostController(final boolean useEventsListeningThread) {

    this.useEventsListeningThread = useEventsListeningThread;
  }

  // ... business methods

  public void connect() {

    final String dynamicSerialChannelPortName = detectSerialPortName(EXPECTING_WELCOME_MESSAGE);
    connect(dynamicSerialChannelPortName);
  }

  public void connect(final String serialChannelPortName) {

    try {

      this.connectedToSerialChannelPortName = serialChannelPortName;
      this.connectedToSerialChannel = openSerialChannel(serialChannelPortName);

      if (useEventsListeningThread) {

        keepEventsListeningThreadRunning = true;

        final Thread eventsListeningThread = new Thread(new SerialChannelEventsListenerRunnable(this));
        eventsListeningThread.start();
      } else {
        keepEventsListeningThreadRunning = false;
      }
    } catch (final Exception ex) {
      throw handleFatalException(ex);
    }
  }

  public void disconnect() {

    keepEventsListeningThreadRunning = false;

    try {

      closeSerialChannel(connectedToSerialChannel);
    } catch (final Exception ex) {
      throw handleFatalException(ex);
    }
  }

  public String getConnectedToPortName() {

    return connectedToSerialChannelPortName;
  }

  public boolean isConnected() {

    return connectedToSerialChannel != null && connectedToSerialChannel.isOpen();
  }

  public boolean isKeepEventsListeningThreadRunning() {

    return keepEventsListeningThreadRunning;
  }

  public void sendData(final byte[] data) {

    try {
      writeData(connectedToSerialChannel, data);
      notifyEventListenersAboutSentData(data);
    } catch (final IOException ex) {
      throw handleFatalException(ex);
    }
  }

  public void sendMessage(final String message) {

    try {
      writeMessage(connectedToSerialChannel, message);
      notifyEventListenersAboutSentMessage(message);
    } catch (final IOException ex) {
      throw handleFatalException(ex);
    }
  }

  public byte[] receiveData() {

    try {

      final byte[] data = readData(connectedToSerialChannel);

      if (data.length > 0) {
        notifyEventListenersAboutReceivedData(data);
      }

      return data;
    } catch (final IOException ex) {
      throw handleFatalException(ex);
    }
  }

  public String receiveMessage() {

    try {

      final String message = readMessage(connectedToSerialChannel);

      if (StringUtils.isNotBlank(message)) {
        notifyEventListenersAboutReceivedMessage(message);
      }

      return message;
    } catch (final IOException ex) {
      throw handleFatalException(ex);
    }
  }

  // ... helper methods

  protected static RuntimeException handleFatalException(final Exception ex) {

    throw new RuntimeException(ex);
  }

  // ... events management methods

  private void notifyEventListenersAboutReceivedData(final byte[] data) {

    eventsListeners //
        .stream() //
        .forEach(eventsListener -> eventsListener.onDataReceived(data));
  }

  private void notifyEventListenersAboutReceivedMessage(final String message) {

    eventsListeners //
        .stream() //
        .forEach(eventsListener -> eventsListener.onMessageReceived(message));
  }

  private void notifyEventListenersAboutSentData(final byte[] data) {

    eventsListeners //
        .stream() //
        .forEach(eventsListener -> eventsListener.onDataSent(data));
  }

  private void notifyEventListenersAboutSentMessage(final String message) {

    eventsListeners //
        .stream() //
        .forEach(eventsListener -> eventsListener.onMessageSent(message));
  }

  public void subscribe(final SerialChannelEventsListener eventsListener) {

    eventsListeners.add(eventsListener);
  }

  public void unsubscribe(final SerialChannelEventsListener eventsListener) {

    eventsListeners.remove(eventsListener);
  }

}
