package app.arduino.sirenofshame.frontend.javafx.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.arduino.sirenofshame.frontend.javafx.exception.util.ErrorHelper;
import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class ConfigurationService {

  // ... constants

  private static final String CURRENT_USER_HOME_DIR = System.getProperty("user.home");
  private static final String CURRENT_WORK_DIR = System.getProperty("user.dir");

  private static final String DEFAULT_PROPERTIES_FILE_NAME = //
      "sirenofshame-host-gui-config.json";

  private static final Path DEFAULT_PROPERTIES_FILE_PATH = //
      Paths //
          .get(CURRENT_WORK_DIR) //
          .resolve(DEFAULT_PROPERTIES_FILE_NAME);

  private static final Path CUSTOM_USER_PROPERTIES_FILE_PATH = //
      Paths //
          .get(CURRENT_USER_HOME_DIR) //
          .resolve(DEFAULT_PROPERTIES_FILE_NAME);

  // ... business methods

  public static JenkinsApiJsonResourceScannerConfig getInitialConfiguration() {

    return loadProperties(CUSTOM_USER_PROPERTIES_FILE_PATH, DEFAULT_PROPERTIES_FILE_PATH);
  }

  public static void saveConfiguration(JenkinsApiJsonResourceScannerConfig config) {

    save(config, CUSTOM_USER_PROPERTIES_FILE_PATH);
  }

  // ... helper methods

  private static JenkinsApiJsonResourceScannerConfig loadProperties(final Path... propertiesFilePathes) {

    for (Path propertiesFilePath : propertiesFilePathes) {

      try {

        return load(propertiesFilePath);
      } catch (Exception ex) {
        // ... nothing to do, try the next path if any.
      }
    }

    throw ErrorHelper.handleFatalException(
        String.format("No one application config file could be loaded: %s.", (Object[]) propertiesFilePathes));
  }

  private static JenkinsApiJsonResourceScannerConfig load(final Path configFilePath) {

    try {

      final ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(configFilePath.toFile(), JenkinsApiJsonResourceScannerConfig.class);
    } catch (final Exception ex) {
      throw ErrorHelper.handleFatalException(ex);
    }
  }

  private static void save(final JenkinsApiJsonResourceScannerConfig config, final Path configFilePath) {

    try {

      final ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(configFilePath.toFile(), config);
    } catch (final Exception ex) {
      throw ErrorHelper.handleFatalException(ex);
    }
  }

  // ... launcher methods

  public static final String HOST_URL = "http://jenkins:80";
  public static final String AUTH_USERNAME = "username";
  public static final String AUTH_PASSWORD = "password";

  public static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs/job/inte-b/";
  public static final Pattern JENKINS_JOB_NAMES_TO_MONITOR__REGEX = Pattern.compile(".+integration");
  public static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

  public static void main(final String[] args) {

    final JenkinsApiJsonResourceScannerConfig config = JenkinsApiJsonResourceScannerConfig.of( //

        JenkinsHttpClientConfig.of( //
            HOST_URL, //
            AUTH_USERNAME, //
            AUTH_PASSWORD //
        ), //
        JENKINS_POLLING_PERIOD__IN_SEC__15, //
        JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS, //
        JENKINS_JOB_NAMES_TO_MONITOR__REGEX //
    );

    save(config, CUSTOM_USER_PROPERTIES_FILE_PATH);

    JenkinsApiJsonResourceScannerConfig loadedConfig = load(CUSTOM_USER_PROPERTIES_FILE_PATH);
    System.out.println(loadedConfig);
  }

}
