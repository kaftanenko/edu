package app.arduino.sirenofshame.frontend.javafx.resources;

import java.util.ResourceBundle;

public enum UIMessage {

  // ... enumeration values

  APP_TITLE("sirenofshame.app-title"),

  EMPTY("sirenofshame.common.empty"),

  ACTION_COMMON_CONNECT("sirenofshame.common.connect"), //
  ACTION_COMMON_DISCONNECT("sirenofshame.common.disconnect"), //

  COMMON_BUTTON_OK("sirenofshame.common.button.ok"), //
  COMMON_BUTTON_CANCEL("sirenofshame.common.button.cancel"), //

  SETTINGS_TITLE("sirenofshame.dialog.settings.title"), //
  SETTINGS_LABEL_HOST_URL("sirenofshame.dialog.settings.label.host-url"), //
  SETTINGS_LABEL_USERNAME("sirenofshame.dialog.settings.label.username"), //
  SETTINGS_LABEL_PASSWORD("sirenofshame.dialog.settings.label.password"), //
  SETTINGS_LABEL_POLLING_PERIOD_IN_SEC("sirenofshame.dialog.settings.label.polling-period-in-sec"), //
  SETTINGS_LABEL_JOBS_FOLDER_PATH("sirenofshame.dialog.settings.label.jobs-folder-path"), //
  SETTINGS_LABEL_JOBS_NAME_REGEX_PATTERN("sirenofshame.dialog.settings.label.jobs-name-regex-pattern"), //

  STATUS_CONNECTED_TO("sirenofshame.common.status.connected-to"), //
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
