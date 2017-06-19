package app.arduino.sireneofshame.frontend.impl.javafx.resources;

import java.util.ResourceBundle;

public enum UIMessage {

	// ... enumeration values

	APP_TITLE("sireneofshame.app-title"),

	EMPTY("sireneofshame.common.empty"),

	ACTION_COMMON_CONNECT("sireneofshame.common.connect"),
	ACTION_COMMON_DISCONNECT("sireneofshame.common.disconnect"),

	STATUS_CONNECTED_TO("sireneofshame.common.status.connected-to"),
	STATUS_DISCONNECTED("sireneofshame.common.status.disconnected"), //

	BUTTON_SET_TO_ALARM_LEVEL("sireneofshame.common.button.set-to-alarm-level."),//
	;

	// ... constants

	private static final String RESOURCE_PATH_LANG_MESSAGES_PROPERTIES = "text.messages";

	// ... properties

	private static ResourceBundle messagesBundle = ResourceBundle.getBundle(RESOURCE_PATH_LANG_MESSAGES_PROPERTIES);

	// ... business methods

	private String messageKey;

	// ... constructors

	private UIMessage(final String messageKey) {

		this.messageKey = messageKey;
	}

	// ... business methods

	public String getKey() {

		return messageKey;
	}

	public String getText() {

		return getMessageText(messageKey);
	}

	public String getText(final String keyPostfix) {

		return getMessageText(messageKey + keyPostfix);
	}

	// ... helper methods

	public static String getMessageText(final String messageKey) {

		return messagesBundle.getString(messageKey);
	}

	public static ResourceBundle getBundle() {

		return messagesBundle;
	}

}
