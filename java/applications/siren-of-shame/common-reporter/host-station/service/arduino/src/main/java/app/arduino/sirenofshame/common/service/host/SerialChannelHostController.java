package app.arduino.sirenofshame.common.service.host;

import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.readBytes;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.writeBytes;

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

    final String dynamicSerialChannelPortName = //
        findSerialChannelByWellcomeMessage(EXPECTING_WELCOME_MESSAGE);
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

  public void sendMessage(final String message) {

    try {
      writeBytes(connectedToSerialChannel, message);
      notifyEventListenersAboutSentMessage(message);
    } catch (final IOException ex) {
      throw handleFatalException(ex);
    }
  }

  public String readMessage() {

    try {

      final String message = readBytes(connectedToSerialChannel);

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

  private void notifyEventListenersAboutSentMessage(final String message) {

    for (final SerialChannelEventsListener eventsListener : eventsListeners) {

      eventsListener.onMessageSent(message);
    }
  }

  private void notifyEventListenersAboutReceivedMessage(final String message) {

    for (final SerialChannelEventsListener eventsListener : eventsListeners) {

      eventsListener.onMessageReceived(message);
    }
  }

  public void subscribe(final SerialChannelEventsListener eventsListener) {

    eventsListeners.add(eventsListener);
  }

  public void unsubscribe(final SerialChannelEventsListener eventsListener) {

    eventsListeners.remove(eventsListener);
  }

}
