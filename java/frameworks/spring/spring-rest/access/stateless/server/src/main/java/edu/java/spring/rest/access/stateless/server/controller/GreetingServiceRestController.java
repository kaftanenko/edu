package edu.java.spring.rest.access.stateless.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.business.greeting.service.GreetingServiceImpl;
import edu.java.spring.rest.access.stateless.server.filter.GreetingServiceRestFilter;
import edu.java.spring.rest.access.stateless.util.LogUtils;

@RestController
public class GreetingServiceRestController {

	// ... constants

	private static final Logger LOG = LoggerFactory.getLogger(GreetingServiceRestFilter.class);

	// ... dependency properties

	private final GreetingService greetingService = new GreetingServiceImpl();

	// ... REST methods

	@RequestMapping("/greeting")
	public Greeting getGreetingFor(@RequestParam(value = "name", defaultValue = "World") String name) {

		LogUtils.logInputParameters("name", name);

		final Greeting result = greetingService.findByName(name);

		LogUtils.logOutputResult(result);
		return result;
	}

}