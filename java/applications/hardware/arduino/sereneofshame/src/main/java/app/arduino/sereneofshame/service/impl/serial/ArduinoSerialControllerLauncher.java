package app.arduino.sereneofshame.service.impl.serial;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import dk.thibaut.serial.SerialChannel;
import dk.thibaut.serial.SerialPort;
import dk.thibaut.serial.enums.BaudRate;
import dk.thibaut.serial.enums.DataBits;
import dk.thibaut.serial.enums.Parity;
import dk.thibaut.serial.enums.StopBits;

public class ArduinoSerialControllerLauncher {

	public static void main(final String[] args) throws IOException {

		// Get a list of available ports names (COM2, COM4, ...)
		final List<String> portsNames = SerialPort.getAvailablePortsNames();

		System.out.println(portsNames);

		// Get a new instance of SerialPort by opening a port.
		final SerialPort port = SerialPort.open("COM9");

		// Configure the connection
		port.setTimeout(100);
		port.setConfig(BaudRate.B9600, Parity.NONE, StopBits.ONE, DataBits.D8);

		// You have the choice, you can either use the Java NIO channels
		// or classic Input/Ouput streams to read and write data.
		final SerialChannel channel = port.getChannel();

		final InputStream istream = port.getInputStream();

		// // Read some data using a stream
		final byte[] byteBuffer = new byte[4096];
		// // Will timeout after 100ms, returning 0 if no bytes were available.
		final int n = istream.read(byteBuffer);

		System.out.println(n);
		final String message = new String(byteBuffer);
		System.out.println(message);

		final String msg = readBytes(channel);

		writeBytes(channel, "red");
		// final OutputStream os = port.getOutputStream();
		// os.write("yellow".getBytes());

		readBytes(channel);

		port.close();
	}

	// ... helper methofds

	private static String readBytes(final SerialChannel channel) throws IOException {

		final ByteBuffer messageBuffer = ByteBuffer.allocate(4096);
		final int readBytes = channel.read(messageBuffer);

		final String message = new String(messageBuffer.array());

		System.out.println("in: (" + readBytes + ") " + message);

		return message;
	}

	private static void writeBytes(final SerialChannel channel, final String message) throws IOException {

		final ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());
		final int writtenBytes = channel.write(messageBuffer);
		channel.write(ByteBuffer.wrap(new byte[] { 0 }));

		System.out.println("out: (" + writtenBytes + ") " + message);
	}

}
