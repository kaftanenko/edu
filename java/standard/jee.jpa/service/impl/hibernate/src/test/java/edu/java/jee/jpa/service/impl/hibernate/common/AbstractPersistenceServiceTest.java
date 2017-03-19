package edu.java.jee.jpa.service.impl.hibernate.common;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.flywaydb.core.Flyway;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import edu.java.jee.jpa.service.impl.common.flyway.FlywayFactory;

public class AbstractPersistenceServiceTest {

	// ... setUp/tearDown

	@BeforeTest
	public void setUp() {

		final Flyway flyway = FlywayFactory.buildFlyway();

		flyway.clean();
		final int i = flyway.migrate();
		System.out.println("### Migrated scripts: " + i);
		flyway.getDataSource();
	}

	@AfterTest
	public void tearDown() {

		// ...
	}

	public static EntityManagerFactory createEntityManagerFactory() {

		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu.java.jee.jpa");
		return emf;
	}
}
