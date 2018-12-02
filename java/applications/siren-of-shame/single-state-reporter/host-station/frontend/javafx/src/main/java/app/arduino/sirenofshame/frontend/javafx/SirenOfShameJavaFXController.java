package app.arduino.sirenofshame.frontend.javafx;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostControllerEventsListener;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.impl.serialchannel.SirenOfShameSingleStateHostSerialChannelController;
import app.sirenofshame.common.host.service.jenkins.client.http.parser.JenkinsApiJsonParser;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerEventsListener;

public class SirenOfShameJavaFXController implements AutoCloseable {

  // ... properties

  private final SirenOfShameJavaFXApplication gui;
  private final SirenOfShameSingleStateHostSerialChannelController sirenOfShameController;

  private final JenkinsApiJsonResourceScanner jenkinsApiJsonScanner;
  private Thread jenkinsApiJsonScannerThread;

  // ... constructors

  public SirenOfShameJavaFXController(final SirenOfShameJavaFXApplication gui) {

    this.gui = gui;

    this.sirenOfShameController = initSirenOfShameController(gui);
    this.jenkinsApiJsonScanner = initJenkinsApiJsonresourceScanner(this, gui.getConfig());
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
      final SirenOfShameJavaFXController sirenOfShameJavaFXController,
      final JenkinsApiJsonResourceScannerConfig config) {

    final JenkinsApiJsonResourceScanner jenkinsApiJsonScanner = new JenkinsApiJsonResourceScanner(config);

    final JenkinsApiJsonResourceScannerEventsListener scannerEventsListener = new JenkinsApiJsonResourceScannerEventsListener() {

      @Override
      public void onBeforeResourceRequest(final String resourcePath) {
      }

      @Override
      public void onAfterResourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

        final Collection<Map<String, Object>> jsonJobNodes = JenkinsApiJsonParser.extractJobNodes(jsonRootNode);
        final Set<String> jobsStatesSummary = JenkinsApiJsonParser.collectJobsStates(jsonJobNodes,
            config.getJobsNameRegExPattern());

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
