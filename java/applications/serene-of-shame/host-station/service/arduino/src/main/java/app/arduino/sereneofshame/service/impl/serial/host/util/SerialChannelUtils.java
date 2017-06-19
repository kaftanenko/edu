package app.arduino.sereneofshame.service.impl.serial.host.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

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
	private static final int SERIAL_PORT_DELAY_BEFORE_FIRST_READ_IN_MS__100 = 500;
	private static final int SERIAL_PORT_DELAY_AFTER_WRITE_IN_MS__100 = 100;
	private static final int SERIAL_PORT_TIMEOUT_IN_MS__1000 = 1000;

	// ... business methods

	public static SerialChannel openSerialChannel(final String portName) throws IOException {

		final SerialPort port = SerialPort.open(portName);

		port.setConfig(SERIAL_PORT_CONFIG);
		port.setTimeout(SERIAL_PORT_TIMEOUT_IN_MS__1000);

		final SerialChannel serialChannel = port.getChannel();
		delayThreadFor(SERIAL_PORT_DELAY_BEFORE_FIRST_READ_IN_MS__100);

		return serialChannel;
	}

	public static void closeSerialChannel(final SerialChannel channel) throws IOException {

		channel.close();
	}

	public static String findSerialChannelByWellcomeMessage(final String expectingWelcomeMessage) {

		final List<String> portsNames = SerialPort.getAvailablePortsNames();

		for (final String portName : portsNames) {

			try {

				System.out.println("Try the port: " + portName);

				final SerialChannel serialChannel = openSerialChannel(portName);
				final String welcomeMessage = readBytes(serialChannel);
				serialChannel.close();

				if (welcomeMessage.startsWith(expectingWelcomeMessage)) {

					return portName;
				}
			} catch (final Exception ex) {
				System.out.println(ex.getMessage());
			}
		}

		throw new RuntimeException("No serial port has responded with expected greeting message.");
	}

	public static String readBytes(final SerialChannel channel) throws IOException {

		final ByteBuffer messageBuffer = ByteBuffer.allocate(1024);
		final int readBytes = channel.read(messageBuffer);

		final String message = (new String(messageBuffer.array())).trim();

		System.out.println("in: (" + readBytes + ") '" + message + "'");

		return message;
	}

	public static void writeBytes(final SerialChannel channel, final String message) throws IOException {

		final ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());
		final int writtenBytes = channel.write(messageBuffer);
		channel.flush(true, true);

		// delayThreadFor(SERIAL_PORT_DELAY_AFTER_WRITE_IN_MS__100);

		System.out.println("out: (" + writtenBytes + ") '" + message + "'");
	}

	// ... helper methods

	private static void delayThreadFor(final long timeInMs) {

		try {
			Thread.sleep(timeInMs);
		} catch (final InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
