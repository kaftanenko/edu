package edu.java.jse.rmi.client.example;

import java.io.Serializable;

import edu.java.jse.rmi.api.ITask;

public class HelloTask implements ITask<String>, Serializable {

	// ... constants

	private static final long serialVersionUID = -5726536771980418646L;

	// ... properties

	private final String personName;

	// ... constructors

	public HelloTask(final String personName) {

		this.personName = personName;
	}

	// ... business methods

	@Override
	public String execute() {

		return "Hello " + personName + "!";
	}

}
