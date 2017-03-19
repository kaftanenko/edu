package edu.java.jse._8.time;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class _Launcher {

	public static void main(final String[] args) {

		final LocalTime _01 = LocalTime.now();
		final LocalTime _02 = LocalTime.now(ZoneId.systemDefault());
		final LocalTime _03 = LocalTime.now(Clock.systemDefaultZone());

		final LocalTime _11 = LocalTime.of(11, 22);
		final LocalTime _12 = LocalTime.of(11, 22, 33);
		final LocalTime _13 = LocalTime.of(11, 22, 33, 444444444);

		final LocalTime _21 = LocalTime.parse("11:22:33");
		final LocalTime _22 = LocalTime.parse("11-22-33", DateTimeFormatter.ofPattern("HH-mm-ss"));

		final LocalTime _31 = LocalTime.MIN;
		final LocalTime _32 = LocalTime.MAX;

		// ...

		System.out.println(_01);
		System.out.println(_02);
		System.out.println(_03);

		System.out.println(_11);
		System.out.println(_12);
		System.out.println(_13);

		System.out.println(_21);
		System.out.println(_22);

		System.out.println(_31);
		System.out.println(_32);
	}

}
