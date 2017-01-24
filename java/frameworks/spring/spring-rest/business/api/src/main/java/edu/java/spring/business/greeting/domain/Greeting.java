package edu.java.spring.business.greeting.domain;

public class Greeting {

	// ... properties

	private final long id;
	private final String content;

	// ... constructors

	public Greeting(long id, String content) {

		this.id = id;
		this.content = content;
	}

	// ... getter methods

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}