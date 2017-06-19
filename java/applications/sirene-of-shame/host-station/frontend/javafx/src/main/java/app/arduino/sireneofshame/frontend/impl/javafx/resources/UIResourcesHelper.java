package app.arduino.sireneofshame.frontend.impl.javafx.resources;

import java.io.InputStream;
import java.util.MissingResourceException;

public class UIResourcesHelper {

	// ... business methods

	public static InputStream getResourceAsStream(final String resourcePath) {

		final InputStream is = UIResourcesHelper.class.getResourceAsStream(resourcePath);
		if (is == null) {
			throw new MissingResourceException("Resource \"" + resourcePath + "\" doesn't found.",
					UIResourcesHelper.class.getName(), resourcePath);
		}
		return is;
	}

}
