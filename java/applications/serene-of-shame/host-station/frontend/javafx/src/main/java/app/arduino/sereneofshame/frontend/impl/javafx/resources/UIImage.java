package app.arduino.sereneofshame.frontend.impl.javafx.resources;

import java.io.InputStream;

public enum UIImage {

	// ... enumeration values

	APPLICATION_LOGO_32("server/icons8-Disconnected-32.png"),
	APPLICATION_LOGO_48("server/icons8-Disconnected-48.png"),

	BUTTON_CONNECT_24("server/icons8-Connected-24.png"),
	BUTTON_DISCONNECT_24("server/icons8-Disconnected-24.png");

	// ... properties

	private static final String BASIC_PATH = "/icons/";

	private String imagePath;

	// ... constructors

	private UIImage(final String imagePath) {

		this.imagePath = imagePath;
	}

	// ... business methods

	public String getPath() {

		return buildFullImagePath(imagePath);
	}

	public InputStream getAsStream() {

		final String fullImagePath = getPath();
		return getImageAsStream(fullImagePath);
	}

	public static InputStream getImageAsStream(final String fullImagePath) {

		return UIResourcesHelper.getResourceAsStream(fullImagePath);
	}

	// ... helper methods

	private static String buildFullImagePath(final String imagePath) {

		return BASIC_PATH + imagePath;
	}

}
