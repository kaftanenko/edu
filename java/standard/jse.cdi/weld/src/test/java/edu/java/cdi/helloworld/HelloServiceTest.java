package edu.java.cdi.helloworld;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.java.cdi.helloworld.api.IHelloService;
import edu.java.cdi.helloworld.impl.localized.LocalizedService;
import edu.java.cdi.util.junit.WeldJUnit4Runner;

@RunWith(WeldJUnit4Runner.class)
public class HelloServiceTest {
	
	// ... properties
	
	@Inject
	@LocalizedService
	private IHelloService helloService;
	
	// ... test methods
	
	@Test
	public void testSayHelloTo_Success() {
	
		// ... prepare test data
		final String NAME_TO_SAY_HELLO_TO = "World";
		final String EXPECTED_SAY_HELLO_TEXT = "Hello World!";
		
		// ... verify preconditions
		
		// ... run operation under test
		final String sayHelloText = helloService.sayHelloTo(NAME_TO_SAY_HELLO_TO);
		
		// ... verify post-conditions
		Assert.assertEquals(EXPECTED_SAY_HELLO_TEXT, sayHelloText);
	}
	
}
