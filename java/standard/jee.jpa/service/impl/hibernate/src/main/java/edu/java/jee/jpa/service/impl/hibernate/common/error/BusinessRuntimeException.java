package edu.java.jee.jpa.service.impl.hibernate.common.error;

public class BusinessRuntimeException extends RuntimeException {

	// ... constants

	private static final long serialVersionUID = -7566864211259699824L;

	// ... constructors

	public BusinessRuntimeException(final Throwable ex) {

		super(ex);
	}

	public BusinessRuntimeException(final String messageText) {

		super(messageText);
	}

	public BusinessRuntimeException(final String messageText, final Throwable ex) {

		super(messageText, ex);
	}

}
