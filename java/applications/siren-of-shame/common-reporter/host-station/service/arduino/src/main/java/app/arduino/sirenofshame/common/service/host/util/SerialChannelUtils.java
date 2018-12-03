package app.arduino.sirenofshame.common.service.host.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
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

  private static final int DEFAULT__CHUNK_SIZE__64_000 = 64000;

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

  public static String detectSerialPortName(final String expectingWellcomeMessage) {

    final long maxReadAttemptsCount = 3;
    final long delayBetweenReadAttempts = 10;

    return detectSerialPortName(expectingWellcomeMessage, maxReadAttemptsCount, delayBetweenReadAttempts);
  }

  public static String detectSerialPortName( //
      final String expectingWellcomeMessage, //
      final long maxReadAttemptsCount, //
      final long delayBetweenReadAttempts //
  ) {

    final List<String> portNames = SerialPort.getAvailablePortsNames();

    LOG.info("Try to negotiate appropriate serial channel by expected wellcome message...");

    for (final String portName : portNames) {

      try (final SerialChannel serialChannel = openSerialChannel(portName)) {

        for (int attemptIndex = 0; attemptIndex < maxReadAttemptsCount; attemptIndex++) {

          final String wellcomeMessage = readMessage(serialChannel);

          if (wellcomeMessage.startsWith(expectingWellcomeMessage)) {

            LOG.info(String.format("... the port responded with expected wellcome message (#%d).", attemptIndex));
            return portName;
          } else {
            LOG.info(String.format("... the port didn't respond with expected wellcome message (#%d).", attemptIndex));
            Thread.sleep(delayBetweenReadAttempts);
          }
        }
      } catch (final Exception ex) {
        LOG.error("... " + ex.getMessage(), ex);
      }
    }

    final String errorMessage = "No serial port has responded with expected wellcome message.";
    LOG.error(errorMessage);

    throw new RuntimeException(errorMessage);
  }

  public static byte[] readData(final SerialChannel channel) throws IOException {

    final byte[] readData = readBytes(channel);

    LOG.info(String.format("... rx: (%d bytes)", readData.length));

    return readData;
  }

  public static String readMessage(final SerialChannel channel) throws IOException {

    final byte[] readData = readBytes(channel);

    final String message = (new String(readData, Charset.forName("UTF8"))).trim();
    LOG.info(String.format("... rx: (%d bytes) \"%s\"", readData.length, message));

    return message;
  }

  private static synchronized byte[] readBytes(final SerialChannel channel) throws IOException {

    final ByteBuffer messageBuffer = ByteBuffer.allocate(1_000_000);

    final int readBytesCount = channel.read(messageBuffer);
    final byte[] readData = Arrays.copyOf(messageBuffer.array(), readBytesCount);

    return readData;
  }

  // ...

  public static void writeData(final SerialChannel channel, final byte[] data) throws IOException {

    final int writtenBytes = writeBytes(channel, data);
    LOG.info(String.format("... tx: (%d bytes)", writtenBytes));

    channel.flush(true, true);
  }

  public static void writeMessage(final SerialChannel channel, final String message) throws IOException {

    final byte[] messageBytes = message.getBytes();
    final int writtenBytesCount = writeBytes(channel, messageBytes);
    LOG.info(String.format("... tx: (%d bytes) \"%s\"", writtenBytesCount, message));

    channel.flush(true, true);
  }

  private static synchronized int writeBytes(final SerialChannel channel, final byte[] messageBytes)
      throws IOException {

    final int bytesToWriteAmount = messageBytes.length;
    int writtenBytesCount = 0;

    int chunkIndex = 0;
    while (writtenBytesCount < bytesToWriteAmount) {

      final int startPos = chunkIndex * DEFAULT__CHUNK_SIZE__64_000;
      final int currentChunkSize = Math.min(DEFAULT__CHUNK_SIZE__64_000, bytesToWriteAmount - writtenBytesCount);
      final byte[] currentChunkData = Arrays.copyOfRange(messageBytes, startPos, startPos + currentChunkSize);

      final ByteBuffer messageBuffer = ByteBuffer.wrap(currentChunkData);

      writtenBytesCount += channel.write(messageBuffer);
      LOG.info(String.format("... tx: (chunk: #%d, chunk size: %d bytes, amount: %d bytes)", //
          chunkIndex, currentChunkSize, writtenBytesCount));

      chunkIndex++;
    }

    return writtenBytesCount;
  }

}
