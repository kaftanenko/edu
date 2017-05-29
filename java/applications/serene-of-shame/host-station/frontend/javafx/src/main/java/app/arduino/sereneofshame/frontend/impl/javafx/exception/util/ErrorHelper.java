package app.arduino.sereneofshame.frontend.impl.javafx.exception.util;

public class ErrorHelper {

	// ... business methods

	public static RuntimeException handleValueIsNotSupportedYet(final String attributeName,
			final Object attributeValue) {

		final String errorMessage = "The value \"" + attributeValue + "\" of the attribute \"" + attributeName
				+ "\" is not supported yet.";
		throw new RuntimeException(errorMessage);
	}

}
