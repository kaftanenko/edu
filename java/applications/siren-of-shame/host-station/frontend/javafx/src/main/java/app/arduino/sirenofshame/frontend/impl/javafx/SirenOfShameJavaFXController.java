package app.arduino.sirenofshame.frontend.impl.javafx;

import static app.sirenofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_PASSWORD;
import static app.sirenofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_USERNAME;
import static app.sirenofshame.host.service.jenkins.client.JenkinsClientConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.service.host.api.SirenOfShameHostControllerEventsListener;
import app.arduino.sirenofshame.service.host.impl.serialport.SirenOfShameHostSerialPortController;
import app.sirenofshame.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.host.service.jenkins.client.json.parser.JenkinsApiJsonParser;
import app.sirenofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScanner;
import app.sirenofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScannerConfig;
import app.sirenofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScannerEventsListener;

public class SirenOfShameJavaFXController implements AutoCloseable {

	// ... constants

	private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs";
	protected static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

	// ... properties

	private final SirenOfShameJavaFXApplication gui;
	private final SirenOfShameHostSerialPortController sirenOfShameController;

	private final JenkinsApiJsonRessourceScanner jenkinsApiJsonScanner;
	private Thread jenkinsApiJsonScannerThread;

	// ... constructors

	public SirenOfShameJavaFXController(final SirenOfShameJavaFXApplication gui) {

		this.gui = gui;

		this.sirenOfShameController = initSirenOfShameController(gui);

		jenkinsApiJsonScanner = initJenkinsApiJsonRessourceScanner(this);
	}

	private static SirenOfShameHostSerialPortController initSirenOfShameController(
			final SirenOfShameJavaFXApplication gui) {

		final SirenOfShameHostSerialPortController sirenOfShameController = new SirenOfShameHostSerialPortController();

		final SirenOfShameHostControllerEventsListener eventsListener = new SirenOfShameHostControllerEventsListener() {
			@Override
			public void onStateChanged(final ESirenOfShameAlarmLevel from, final ESirenOfShameAlarmLevel to) {
				gui.onAlarmLevelChanged(to);
			}
		};
		sirenOfShameController.subscribe(eventsListener);

		return sirenOfShameController;
	}

	private static JenkinsApiJsonRessourceScanner initJenkinsApiJsonRessourceScanner(
			final SirenOfShameJavaFXController sirenOfShameJavaFXController) {

		final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of(HOST_URL, AUTH_USERNAME, AUTH_PASSWORD);
		final JenkinsApiJsonRessourceScannerConfig jenkinsJobsStateScannerConfig = JenkinsApiJsonRessourceScannerConfig
				.of(config, JENKINS_POLLING_PERIOD__IN_SEC__15 * 1000, JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS);
		final JenkinsApiJsonRessourceScanner jenkinsApiJsonScanner = new JenkinsApiJsonRessourceScanner(
				jenkinsJobsStateScannerConfig);

		final JenkinsApiJsonRessourceScannerEventsListener scannerEventsListener = new JenkinsApiJsonRessourceScannerEventsListener() {

			@Override
			public void onBeforeRessourceRequest(final String resourcePath) {
			}

			@Override
			public void onAfterRessourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

				final Collection<Map<String, Object>> jsonJobsNode = JenkinsApiJsonParser.extractJobsNode(jsonRootNode);
				final Set<String> jobsStatesSummary = JenkinsApiJsonParser.collectJobsStates(jsonJobsNode);

				final ESirenOfShameAlarmLevel highestState;
				if (jobsStatesSummary.contains("red")) {
					highestState = ESirenOfShameAlarmLevel.RED;
				} else if (jobsStatesSummary.contains("red_anime")) {
					highestState = ESirenOfShameAlarmLevel.RED_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("yellow")) {
					highestState = ESirenOfShameAlarmLevel.YELLOW;
				} else if (jobsStatesSummary.contains("yellow_anime")) {
					highestState = ESirenOfShameAlarmLevel.YELLOW_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("blue_anime")) {
					highestState = ESirenOfShameAlarmLevel.GREENBLUE_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("blue")) {
					highestState = ESirenOfShameAlarmLevel.GREENBLUE;
				} else {
					highestState = ESirenOfShameAlarmLevel.UNDEFINED;
				}
				sirenOfShameJavaFXController.setAlarmLevelTo(highestState);
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

		final String portName = sirenOfShameController.getPortName();
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

	public void setAlarmLevelTo(final ESirenOfShameAlarmLevel newAlarmLevel) {

		final ESirenOfShameAlarmLevel currentState = sirenOfShameController.getCurrentAlarmLevel();
		if (currentState != newAlarmLevel) {

			sirenOfShameController.setAlarmLevelTo(newAlarmLevel);
			gui.onAlarmLevelChanged(newAlarmLevel);
		}
	}

	public void showPropertiesDialog() {

		gui.showPropertiesDialog();
	}

}
