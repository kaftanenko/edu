package edu.java.jee.jpa.service.impl.hibernate.common.error;

public class BusinessRuntimeImplementationException extends RuntimeException {

	// ... constants

	private static final long serialVersionUID = -1126221140489810971L;

	// ... constructors

	public BusinessRuntimeImplementationException(final Throwable ex) {

		super(ex);
	}

	public BusinessRuntimeImplementationException(final String messageText) {

		super(messageText);
	}

	public BusinessRuntimeImplementationException(final String messageText, final Throwable ex) {

		super(messageText, ex);
	}

}
