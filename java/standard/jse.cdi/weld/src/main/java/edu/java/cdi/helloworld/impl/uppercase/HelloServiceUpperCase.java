package edu.java.cdi.helloworld.impl.uppercase;

import javax.inject.Inject;

import edu.java.cdi.helloworld.api.IHelloService;
import edu.java.cdi.helloworld.impl.localized.LocalizedService;

@UpperCaseService
public class HelloServiceUpperCase implements IHelloService {
	
	// ... properties
	
	@Inject
	@LocalizedService
	private IHelloService helloService;
	
	// ... business methods
	
	@Override
	public String sayHelloTo(String name) {
	
		return helloService.sayHelloTo(name).toUpperCase();
	}
	
}
