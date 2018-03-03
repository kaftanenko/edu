package app.arduino.sirenofshame.common.service.host.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

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

    private static final SerialConfig SERIAL_PORT_CONFIG = new SerialConfig( //
            BaudRate.B9600, //
            Parity.NONE, //
            StopBits.ONE, //
            DataBits.D8 //
    );
    private static final int SERIAL_PORT_DELAY_BEFORE_FIRST_READ_IN_MS__100 = 100;
    private static final int SERIAL_PORT_TIMEOUT_IN_MS__1000 = 1000;

    private static final Logger LOG = SerialChannelLogManager.getSerialPortChannelLogger();

    // ... business methods

    public static SerialChannel openSerialChannel(final String portName) throws IOException {

        final SerialPort port = SerialPort.open(portName);

        port.setConfig(SERIAL_PORT_CONFIG);
        port.setTimeout(SERIAL_PORT_TIMEOUT_IN_MS__1000);

        LOG.info("Try to open the serial port \"" + portName + "\" ...");

        final SerialChannel serialChannel = port.getChannel();
        delayThreadFor(SERIAL_PORT_DELAY_BEFORE_FIRST_READ_IN_MS__100);

        LOG.info("... succeeded.");

        return serialChannel;
    }

    public static void closeSerialChannel(final SerialChannel serialChannel) throws IOException {

        LOG.info("Try to close the serial channel...");

        serialChannel.close();

        LOG.info("... succeeded.");
    }

    public static String findSerialChannelByWellcomeMessage(final String expectingWelcomeMessage) {

        final List<String> portsNames = SerialPort.getAvailablePortsNames();

        LOG.info("Try to negotiate appropriate serial channel by expected wellcome message...");

        for (final String portName : portsNames) {

            try {

                final SerialChannel serialChannel = openSerialChannel(portName);
                final String welcomeMessage = readBytes(serialChannel);
                serialChannel.close();

                if (welcomeMessage.startsWith(expectingWelcomeMessage)) {

                    LOG.info("... the port responded with expected wellcome message.");
                    return portName;
                } else {
                    LOG.info("... the port didn't respond with expected wellcome message.");
                }
            } catch (final Exception ex) {
                LOG.error("... " + ex.getMessage());
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

        LOG.info("... rx: (" + readBytes + " bytes) \"" + message + "\"");

        return message;
    }

    public static void writeBytes(final SerialChannel channel, final String message) throws IOException {

        final ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());
        final int writtenBytes = channel.write(messageBuffer);
        channel.flush(true, true);

        // delayThreadFor(SERIAL_PORT_DELAY_AFTER_WRITE_IN_MS__100);

        LOG.info("... tx: (" + writtenBytes + " bytes) \"" + message + "\"");
    }

    // ... helper methods

    private static void delayThreadFor(final long timeInMs) {

        try {
            Thread.sleep(timeInMs);
        } catch (final InterruptedException ex) {
            LOG.error(ex);
        }
    }

}
