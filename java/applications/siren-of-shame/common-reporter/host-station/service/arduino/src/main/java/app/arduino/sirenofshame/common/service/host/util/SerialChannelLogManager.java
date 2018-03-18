package app.arduino.sirenofshame.common.service.host.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerialChannelLogManager {

  // ... dependencies

  private static final Logger SIREN_OF_SHAME_HOST_LOG = LogManager.getLogger("SIREN-OF-SHAME_HOST");

  private static final Logger SERIAL_PORT_CHANNEL_LOG = LogManager.getLogger("SERIAL_PORT_CHANNEL");

  // ... business methods

  public static Logger getLogger() {

    return SIREN_OF_SHAME_HOST_LOG;
  }

  public static Logger getSerialPortChannelLogger() {

    return SERIAL_PORT_CHANNEL_LOG;
  }

}
