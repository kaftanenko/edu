package edu.java.jee.jpa.service.impl.hibernate.common.error;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class BusinessErrorHelper {

	// ... business methods

	public static RuntimeException handleFatalException(final Exception ex) {

		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		} else {
			throw new BusinessRuntimeException(ex);
		}
	}

	public static RuntimeException handleFatalException(final String errorMessage) {

		throw new BusinessRuntimeException(errorMessage);
	}

	public static RuntimeException handleFatalException(final String errorMessage, final Exception ex) {

		throw new BusinessRuntimeException(errorMessage, ex);
	}

	// ...

	public static RuntimeException handleConstraintsViolationException(
			final Set<ConstraintViolation<Object>> constraintViolations) {

		final StringBuilder sb = new StringBuilder("Die Datenvalidierung ist fehlgeschlagen. Weitere Details:");
		sb.append("\n");
		for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {

			sb.append(" ");
			sb.append(constraintViolation.getRootBeanClass());
			sb.append(".");
			sb.append(constraintViolation.getPropertyPath());
			sb.append(" ");
			sb.append(constraintViolation.getMessage());
			sb.append("\n");
		}

		final String errorMessage = sb.toString();
		throw handleFatalException(errorMessage);
	}

	// ...

	public static RuntimeException handleImplementationException(final String errorMessage) {

		throw new BusinessRuntimeImplementationException(errorMessage);
	}

	public static RuntimeException handleImplementationException(final String errorMessage, final Exception ex) {

		throw new BusinessRuntimeImplementationException(errorMessage, ex);
	}

	// ...

	public static RuntimeException handleNotImplementedYetException() {

		throw handleImplementationException("... not implemented yet.");
	}

	public static RuntimeException handleThisMethodMustBeNeverCalledException() {

		throw new IllegalAccessError("It's only dummy operation, that must be never called.");
	}

	public static RuntimeException handleThisValueIsNotSupportedYetException(final Object value) {

		final String errorMessage = "Der Wert \"" + value + "\" wird aktuell nicht unterstützt.";
		throw handleImplementationException(errorMessage);
	}

}
