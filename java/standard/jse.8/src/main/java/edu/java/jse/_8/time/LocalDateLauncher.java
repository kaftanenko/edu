package edu.java.jse._8.time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateLauncher {

	public static void main(final String[] args) {

		final LocalDate _01 = LocalDate.now();
		final LocalDate _02 = LocalDate.now(ZoneId.systemDefault());
		final LocalDate _03 = LocalDate.now(Clock.systemDefaultZone());

		final LocalDate _11 = LocalDate.of(1976, 11, 24);
		final LocalDate _12 = LocalDate.of(1976, Month.NOVEMBER, 24);
		final LocalDate _13 = LocalDate.ofEpochDay(2519);
		final LocalDate _14 = LocalDate.ofYearDay(1976, 329);

		final LocalDate _21 = LocalDate.parse("1976-11-24");
		final LocalDate _22 = LocalDate.parse("24.11.1976", DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		final LocalDate _31 = LocalDate.MIN;
		final LocalDate _32 = LocalDate.MAX;

		// ...

		System.out.println(_01);
		System.out.println(_02);
		System.out.println(_03);

		System.out.println(_11);
		System.out.println(_12);
		System.out.println(_13);
		System.out.println(_14);

		System.out.println(_21);
		System.out.println(_22);

		System.out.println(_31);
		System.out.println(_32);
	}

}
