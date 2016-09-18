package edu.java.cdi.helloworld.impl.localized;

import edu.java.cdi.helloworld.api.IHelloService;

@LocalizedService(lang = SupportedLanguages.RUSSIAN)
public class HelloServiceRussian implements IHelloService {
	
	// ... business methods
	
	@Override
	public String sayHelloTo(String name) {
	
		return "Здравствуй " + name + "!";
	}
	
}
