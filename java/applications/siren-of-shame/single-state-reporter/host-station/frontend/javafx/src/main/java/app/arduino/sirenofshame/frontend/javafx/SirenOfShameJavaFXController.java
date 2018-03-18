package app.arduino.sirenofshame.frontend.javafx;

import static app.arduino.sirenofshame.frontend.javafx.JenkinsHttpClientConstants.AUTH_PASSWORD;
import static app.arduino.sirenofshame.frontend.javafx.JenkinsHttpClientConstants.AUTH_USERNAME;
import static app.arduino.sirenofshame.frontend.javafx.JenkinsHttpClientConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostControllerEventsListener;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.impl.serialchannel.SirenOfShameSingleStateHostSerialChannelController;
import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.parser.JenkinsApiJsonParser;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerEventsListener;

public class SirenOfShameJavaFXController implements AutoCloseable {

  // ... constants

  private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs/job/inte-b/";
  private static final Pattern JENKINS_JOB_NAMES_TO_MONITOR__REGEX = Pattern.compile(".+integration");
  protected static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

  // ... properties

  private final SirenOfShameJavaFXApplication gui;
  private final SirenOfShameSingleStateHostSerialChannelController sirenOfShameController;

  private final JenkinsApiJsonResourceScanner jenkinsApiJsonScanner;
  private Thread jenkinsApiJsonScannerThread;

  // ... constructors

  public SirenOfShameJavaFXController(final SirenOfShameJavaFXApplication gui) {

    this.gui = gui;

    this.sirenOfShameController = initSirenOfShameController(gui);

    jenkinsApiJsonScanner = initJenkinsApiJsonresourceScanner(this);
  }

  private static SirenOfShameSingleStateHostSerialChannelController initSirenOfShameController(
      final SirenOfShameJavaFXApplication gui) {

    final SirenOfShameSingleStateHostSerialChannelController sirenOfShameController = new SirenOfShameSingleStateHostSerialChannelController();

    final SirenOfShameSingleStateHostControllerEventsListener eventsListener = new SirenOfShameSingleStateHostControllerEventsListener() {
      @Override
      public void onStateChanged(final ESirenOfShameAlarmLevel from, final ESirenOfShameAlarmLevel to) {
        gui.onAlarmLevelChanged(to);
      }
    };
    sirenOfShameController.subscribe(eventsListener);

    return sirenOfShameController;
  }

  private static JenkinsApiJsonResourceScanner initJenkinsApiJsonresourceScanner(
      final SirenOfShameJavaFXController sirenOfShameJavaFXController) {

    final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of(HOST_URL, AUTH_USERNAME, AUTH_PASSWORD);
    final JenkinsApiJsonResourceScannerConfig jenkinsJobsStateScannerConfig = JenkinsApiJsonResourceScannerConfig
        .of(config, JENKINS_POLLING_PERIOD__IN_SEC__15 * 1000, JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS);
    final JenkinsApiJsonResourceScanner jenkinsApiJsonScanner = new JenkinsApiJsonResourceScanner(
        jenkinsJobsStateScannerConfig);

    final JenkinsApiJsonResourceScannerEventsListener scannerEventsListener = new JenkinsApiJsonResourceScannerEventsListener() {

      @Override
      public void onBeforeResourceRequest(final String resourcePath) {
      }

      @Override
      public void onAfterResourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

        final Collection<Map<String, Object>> jsonJobNodes = JenkinsApiJsonParser.extractJobNodes(jsonRootNode);
        final Set<String> jobsStatesSummary = JenkinsApiJsonParser.collectJobsStates(jsonJobNodes,
            JENKINS_JOB_NAMES_TO_MONITOR__REGEX);

        final ESirenOfShameAlarmLevel highestState;
        if (jobsStatesSummary.contains("red")) {
          highestState = ESirenOfShameAlarmLevel.RED;
        } else if (jobsStatesSummary.contains("red_anime")) {
          highestState = ESirenOfShameAlarmLevel.RED_EXPECTING_UPDATE;
        } else if (jobsStatesSummary.contains("yellow") || jobsStatesSummary.contains("aborted")) {
          highestState = ESirenOfShameAlarmLevel.YELLOW;
        } else if (jobsStatesSummary.contains("yellow_anime") || jobsStatesSummary.contains("aborted_anime")) {
          highestState = ESirenOfShameAlarmLevel.YELLOW_EXPECTING_UPDATE;
        } else if (jobsStatesSummary.contains("blue_anime")) {
          highestState = ESirenOfShameAlarmLevel.GREENBLUE_EXPECTING_UPDATE;
        } else if (jobsStatesSummary.contains("blue")) {
          highestState = ESirenOfShameAlarmLevel.GREENBLUE;
        } else {
          highestState = ESirenOfShameAlarmLevel.UNDEFINED;
        }
        sirenOfShameJavaFXController.updateAlarmLevelTo(highestState);
      }
    };
    jenkinsApiJsonScanner.subscribe(scannerEventsListener);

    return jenkinsApiJsonScanner;
  }

  @Override
  public void close() throws Exception {

    jenkinsApiJsonScannerThread.interrupt();
  }

  // ... business methods

  public void connect() {

    sirenOfShameController.connect();

    final String portName = sirenOfShameController.getConnectedToPortName();
    final ESirenOfShameAlarmLevel alarmLevel = sirenOfShameController.getCurrentAlarmLevel();

    gui.onConnected(portName, alarmLevel);

    jenkinsApiJsonScannerThread = new Thread(jenkinsApiJsonScanner);
    jenkinsApiJsonScannerThread.start();
  }

  public void disconnect() {

    jenkinsApiJsonScannerThread.interrupt();
    sirenOfShameController.disconnect();

    gui.onDisconnected();
  }

  public void updateAlarmLevelTo(final ESirenOfShameAlarmLevel newAlarmLevel) {

    final ESirenOfShameAlarmLevel currentState = sirenOfShameController.getCurrentAlarmLevel();
    if (currentState != newAlarmLevel) {

      // sirenOfShameController.setAlarmLevelTo(newAlarmLevel);
      gui.onAlarmLevelChanged(newAlarmLevel);
    }
  }

  public void showPropertiesDialog() {

    gui.showPropertiesDialog();
  }

}
