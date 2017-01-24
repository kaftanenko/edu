package edu.java.spring.rest.access.stateless.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.rest.access.stateless.client.service.GreetingRestClientServiceImpl;

@SpringBootApplication
public class ClientApplicationLauncher implements CommandLineRunner {

	// ... constants

	private static final Logger LOG = LoggerFactory.getLogger(ClientApplicationLauncher.class);

	// ... dependency properties

	private GreetingService greetingService = new GreetingRestClientServiceImpl();

	// ... launcher methods

	@Override
	public void run(String... args) throws Exception {

		final Greeting greeting = greetingService.findByName("World");

		LOG.info(greeting.toString());
	}

	public static void main(String args[]) {

		SpringApplication.run(ClientApplicationLauncher.class);
	}

}