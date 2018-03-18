package app.arduino.sirenofshame.common.service.host.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.common.service.host.SerialChannelHostController;

public class SerialChannelEventsListenerRunnable implements Runnable {

  // ... constants

  private static final long DEFAULT__SERIAL_CHANNEL__POLLING_TAKT__IN_MS__1000 = 1000;

  // ... dependencies

  private final Logger LOG = LogManager.getLogger(this);

  // ... properties

  private final long serialChannelPollingTaktInMs;
  private final SerialChannelHostController serialChannelHostController;

  // ... constructors

  public SerialChannelEventsListenerRunnable(final SerialChannelHostController serialChannelHostController) {

    this(serialChannelHostController, DEFAULT__SERIAL_CHANNEL__POLLING_TAKT__IN_MS__1000);
  }

  public SerialChannelEventsListenerRunnable(final SerialChannelHostController serialChannelHostController,
      final long serialChannelPollingTaktInMs) {

    this.serialChannelHostController = serialChannelHostController;
    this.serialChannelPollingTaktInMs = serialChannelPollingTaktInMs;
  }

  // ... business methods

  @Override
  public void run() {

    while (serialChannelHostController.isKeepEventsListeningThreadRunning()) {

      try {

        serialChannelHostController.readMessage();
        Thread.sleep(serialChannelPollingTaktInMs);
      } catch (final InterruptedException ex) {
        LOG.error("The events listener thread interrupted.", ex);
      }
    }
  }

}
