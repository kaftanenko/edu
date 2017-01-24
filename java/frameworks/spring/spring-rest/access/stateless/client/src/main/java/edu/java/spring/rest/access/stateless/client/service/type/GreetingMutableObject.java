package edu.java.spring.rest.access.stateless.client.service.type;

import edu.java.spring.business.greeting.domain.Greeting;

public class GreetingMutableObject extends Greeting {

	// ... properties

	private long id;
	private String content;

	// ... constructors

	public GreetingMutableObject() {

		super(0, null);
	}

	public GreetingMutableObject(long id, String content) {

		this();
		this.id = id;
		this.content = content;
	}

	// ... getter/setter methods

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {

		return "Greeting [id=" + id + ", content=" + content + "]";
	}

	// ...

}