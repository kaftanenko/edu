package edu.java.jee.jpa.service.impl.common.flyway;

import java.io.IOException;
import java.util.Properties;

import org.flywaydb.core.Flyway;

public class FlywayFactory {

	// ... business methods

	public static Flyway buildFlyway() {

		final Properties flywayProperties = new Properties();
		try {
			flywayProperties.load(FlywayFactory.class.getResourceAsStream("/flyway.properties"));
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}

		final Flyway flywayInstance = new Flyway();
		flywayInstance.configure(flywayProperties);

		return flywayInstance;
	}

}
