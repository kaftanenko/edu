package edu.java.fws.web.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "helloWorldBean")
@SessionScoped
public class HelloWorldBean {

	// ... business methods

	public String getWorldGreetingMessage() {

		return buildGreetingMessage("World");
	}

	// ... helper methods

	private String buildGreetingMessage(final String name) {

		return "Hello " + name + "!";
	}

}