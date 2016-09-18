package edu.java.cdi.helloworld.impl.localized;

import edu.java.cdi.helloworld.api.IHelloService;

@LocalizedService(lang = SupportedLanguages.GERMAN)
public class HelloServiceGerman implements IHelloService {
	
	// ... business methods
	
	@Override
	public String sayHelloTo(String name) {
	
		return "Hallo " + name + "!";
	}
	
}
