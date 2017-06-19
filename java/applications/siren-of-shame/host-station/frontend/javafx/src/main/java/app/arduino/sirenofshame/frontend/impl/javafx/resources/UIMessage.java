package app.arduino.sirenofshame.frontend.impl.javafx.resources;

import java.util.ResourceBundle;

public enum UIMessage {

	// ... enumeration values

	APP_TITLE("sirenofshame.app-title"),

	EMPTY("sirenofshame.common.empty"),

	ACTION_COMMON_CONNECT("sirenofshame.common.connect"),
	ACTION_COMMON_DISCONNECT("sirenofshame.common.disconnect"),

	STATUS_CONNECTED_TO("sirenofshame.common.status.connected-to"),
	STATUS_DISCONNECTED("sirenofshame.common.status.disconnected"), //

	BUTTON_SET_TO_ALARM_LEVEL("sirenofshame.common.button.set-to-alarm-level."),//
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
