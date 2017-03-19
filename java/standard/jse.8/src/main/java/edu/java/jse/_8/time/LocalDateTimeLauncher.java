package edu.java.jse._8.time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeLauncher {

	public static void main(final String[] args) {

		final LocalDate _date = LocalDate.of(1976, Month.NOVEMBER, 24);
		final LocalTime _time = LocalTime.of(11, 22, 33, 444444444);

		final LocalDateTime _01 = LocalDateTime.now();
		final LocalDateTime _02 = LocalDateTime.now(ZoneId.systemDefault());
		final LocalDateTime _03 = LocalDateTime.now(Clock.systemDefaultZone());

		final LocalDateTime _04 = LocalDateTime.of(_date, _time);
		final LocalDateTime _05 = LocalDateTime.of(1976, 11, 24, 11, 22);
		final LocalDateTime _06 = LocalDateTime.of(1976, Month.NOVEMBER, 24, 11, 22);
		final LocalDateTime _07 = LocalDateTime.of(1976, 11, 24, 11, 22, 33);
		final LocalDateTime _08 = LocalDateTime.of(1976, Month.NOVEMBER, 24, 11, 22, 33);
		final LocalDateTime _09 = LocalDateTime.of(1976, 11, 24, 11, 22, 33, 444444444);
		final LocalDateTime _10 = LocalDateTime.of(1976, Month.NOVEMBER, 24, 11, 22, 33, 444444444);

		final LocalDateTime _11 = _date.atTime(_time);
		final LocalDateTime _12 = _time.atDate(_date);
		final LocalDateTime _13 = _date.atTime(11, 22);
		final LocalDateTime _14 = _date.atTime(11, 22, 33);
		final LocalDateTime _15 = _date.atTime(11, 22, 33, 444444444);

		final LocalDateTime _21 = LocalDateTime.parse("24.11.1976 11-22-33.444444444",
				DateTimeFormatter.ofPattern("dd.MM.yyyy HH-mm-ss.nnnnnnnnn"));

		final LocalDateTime _31 = LocalDateTime.MIN;
		final LocalDateTime _32 = LocalDateTime.MAX;

		// ...

		System.out.println(_01);
		System.out.println(_02);
		System.out.println(_03);
		System.out.println(_04);
		System.out.println(_05);
		System.out.println(_06);
		System.out.println(_07);
		System.out.println(_08);
		System.out.println(_09);
		System.out.println(_10);

		System.out.println(_11);
		System.out.println(_12);
		System.out.println(_13);
		System.out.println(_14);
		System.out.println(_15);

		System.out.println(_21);

		System.out.println(_31);
		System.out.println(_32);
	}

}
