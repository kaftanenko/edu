package app.arduino.sereneofshame.service.impl.serial.host.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import dk.thibaut.serial.SerialChannel;
import dk.thibaut.serial.SerialPort;
import dk.thibaut.serial.enums.BaudRate;
import dk.thibaut.serial.enums.DataBits;
import dk.thibaut.serial.enums.Parity;
import dk.thibaut.serial.enums.StopBits;

public class SerialChannelUtils {

	// ... constants

	public static final int TIMEOUT_PORT_NEGOTIOATION_IN_MS__500 = 500;

	// ... business methods

	public static void closeSerialChannel(final SerialChannel channel) throws IOException {

		channel.close();
	}

	public static SerialChannel findSerialChannelByAndOpenIt(final String greetingMessagePrefix) {

		final List<String> portsNames = SerialPort.getAvailablePortsNames();

		for (final String portName : portsNames) {

			try {
				System.out.println("Try the port: " + portName);
				final SerialPort port = SerialPort.open(portName);

				port.setTimeout(1000);
				port.setConfig(BaudRate.B9600, Parity.NONE, StopBits.ONE, DataBits.D8);

				Thread.sleep(TIMEOUT_PORT_NEGOTIOATION_IN_MS__500);

				final SerialChannel channel = port.getChannel();

				final String greetingMessage = readBytes(channel);
				if (greetingMessage.startsWith(greetingMessagePrefix)) {

					return channel;
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
		final int wroteBytes = channel.write(messageBuffer);

		System.out.println("out: (" + wroteBytes + ") '" + message + "'");
	}

}
