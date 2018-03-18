package app.arduino.sirenofshame.common.service.host.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dk.thibaut.serial.SerialChannel;
import dk.thibaut.serial.SerialConfig;
import dk.thibaut.serial.SerialPort;
import dk.thibaut.serial.enums.BaudRate;
import dk.thibaut.serial.enums.DataBits;
import dk.thibaut.serial.enums.Parity;
import dk.thibaut.serial.enums.StopBits;

public class SerialChannelUtils {

  // ... constants

  private static final SerialConfig DEFAULT__SERIAL_PORT_CONFIG = new SerialConfig( //
      BaudRate.B9600, //
      Parity.NONE, //
      StopBits.ONE, //
      DataBits.D8 //
  );
  private static final int DEFAULT__SERIAL_PORT_TIMEOUT_IN_MS__1000 = 1000;

  // ... dependencies

  private static final Logger LOG = LogManager.getLogger(SerialChannelUtils.class);

  // ... business methods

  public static SerialChannel openSerialChannel(final String portName) throws IOException {

    final SerialPort port = SerialPort.open(portName);

    port.setConfig(DEFAULT__SERIAL_PORT_CONFIG);
    port.setTimeout(DEFAULT__SERIAL_PORT_TIMEOUT_IN_MS__1000);

    LOG.info("Try to open the serial port \"" + portName + "\" ...");

    final SerialChannel serialChannel = port.getChannel();

    if (serialChannel.isOpen()) {

      LOG.info("... succeeded.");
    } else {
      LOG.info("... failed.");
    }

    return serialChannel;
  }

  public static void closeSerialChannel(final SerialChannel serialChannel) throws IOException {

    LOG.info("Try to close the serial channel...");

    serialChannel.close();

    LOG.info("... succeeded.");
  }

  public static String findSerialChannelByWellcomeMessage(final String expectingWellcomeMessage) {

    final long maxReadAttemptsCount = 1;
    final long delayBetweenReadAttempts = 0;

    return findSerialChannelByWellcomeMessage(expectingWellcomeMessage, maxReadAttemptsCount, delayBetweenReadAttempts);
  }

  public static String findSerialChannelByWellcomeMessage( //
      final String expectingWellcomeMessage, //
      final long maxReadAttemptsCount, //
      final long delayBetweenReadAttempts //
  ) {

    final List<String> portsNames = SerialPort.getAvailablePortsNames();

    LOG.info("Try to negotiate appropriate serial channel by expected wellcome message...");

    for (final String portName : portsNames) {

      for (int attemptIndex = 0; attemptIndex < maxReadAttemptsCount; attemptIndex++) {

        try {

          final SerialChannel serialChannel = openSerialChannel(portName);
          final String wellcomeMessage = readBytes(serialChannel);
          serialChannel.close();

          if (wellcomeMessage.startsWith(expectingWellcomeMessage)) {

            LOG.info(String.format("... the port responded with expected wellcome message (#%d).", attemptIndex));
            return portName;
          } else {
            LOG.info(String.format("... the port didn't respond with expected wellcome message (#%d).", attemptIndex));
            Thread.sleep(delayBetweenReadAttempts);
          }
        } catch (final Exception ex) {
          LOG.error("... " + ex.getMessage(), ex);
        }
      }
    }

    final String errorMessage = "No serial port has responded with expected wellcome message.";
    LOG.error(errorMessage);

    throw new RuntimeException(errorMessage);
  }

  public static String readBytes(final SerialChannel channel) throws IOException {

    final ByteBuffer messageBuffer = ByteBuffer.allocate(1024);
    final int readBytes = channel.read(messageBuffer);

    final String message = (new String(messageBuffer.array())).trim();

    LOG.info(String.format("... rx: (%d bytes) \"%s\"", readBytes, message));

    return message;
  }

  public static void writeBytes(final SerialChannel channel, final String message) throws IOException {

    final ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());
    final int writtenBytes = channel.write(messageBuffer);
    channel.flush(true, true);

    LOG.info(String.format("... tx: (%d bytes) \"%s\"", writtenBytes, message));
  }

}
