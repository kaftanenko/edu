package app.arduino.sirenofshame.frontend.javafx.resources;

import java.io.InputStream;

public enum UIImage {

  // ... enumeration values

  APPLICATION_LOGO_36("jenkins/head.png"),

  BUTTON_CONNECT_32("server/switch-on-icon-32.png"), //
  BUTTON_DISCONNECT_32("server/switch-off-icon-32.png"), //
  BUTTON_CONFIGURATION_32("server/settings-icon_3-32.png"), //

  JENKINS_STATE_RED_32("jenkins/state/red.png"), //
  JENKINS_STATE_RED_ANIME_32("jenkins/state/red.png"), //

  JENKINS_STATE_YELLOW_32("jenkins/state/yellow.png"), //
  JENKINS_STATE_YELLOW_ANIME_32("jenkins/state/yellow.png"), //

  JENKINS_STATE_BLUE_32("jenkins/state/blue.png"), //
  JENKINS_STATE_BLUE_ANIME_32("jenkins/state/blue.png"), //

  JENKINS_STATE_DISABLED_32("jenkins/state/disabled.png"), //
  //
  ;

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
