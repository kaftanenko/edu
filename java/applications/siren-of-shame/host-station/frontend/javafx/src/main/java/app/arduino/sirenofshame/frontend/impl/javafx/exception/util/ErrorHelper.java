package app.arduino.sirenofshame.frontend.impl.javafx.exception.util;

public class ErrorHelper {

	// ... business methods

	public static RuntimeException handleFatalException(final Exception ex) {

		throw new RuntimeException(ex);
	}

	public static RuntimeException handleFatalException(final String message) {

		throw new RuntimeException(message);
	}

	public static RuntimeException handleValueIsNotSupportedYetException(final String attributeName,
			final Object attributeValue) {

		final String errorMessage = "The value \"" + attributeValue + "\" of the attribute \"" + attributeName
				+ "\" is not supported yet.";
		throw handleFatalException(errorMessage);
	}

}
