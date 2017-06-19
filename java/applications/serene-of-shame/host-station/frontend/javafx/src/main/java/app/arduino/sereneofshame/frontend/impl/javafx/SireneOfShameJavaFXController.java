package app.arduino.sereneofshame.frontend.impl.javafx;

import static app.sereneofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_PASSWORD;
import static app.sereneofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_USERNAME;
import static app.sereneofshame.host.service.jenkins.client.JenkinsClientConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.arduino.sereneofshame.service.host.api.ESireneOfShameAlarmLevel;
import app.arduino.sereneofshame.service.host.api.SireneOfShameHostControllerEventsListener;
import app.arduino.sereneofshame.service.host.impl.serialport.SireneOfShameHostSerialPortController;
import app.sereneofshame.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sereneofshame.host.service.jenkins.client.json.parser.JenkinsApiJsonParser;
import app.sereneofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScanner;
import app.sereneofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScannerConfig;
import app.sereneofshame.host.service.jenkins.client.json.scanner.JenkinsApiJsonRessourceScannerEventsListener;

public class SireneOfShameJavaFXController implements AutoCloseable {

	// ... constants

	private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs";
	protected static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

	// ... properties

	private final SireneOfShameJavaFXApplication gui;
	private final SireneOfShameHostSerialPortController sireneOfShameController;

	private final JenkinsApiJsonRessourceScanner jenkinsApiJsonScanner;
	private Thread jenkinsApiJsonScannerThread;

	// ... constructors

	public SireneOfShameJavaFXController(final SireneOfShameJavaFXApplication gui) {

		this.gui = gui;

		this.sireneOfShameController = initSireneOfShameController(gui);

		jenkinsApiJsonScanner = initJenkinsApiJsonRessourceScanner(this);
	}

	private static SireneOfShameHostSerialPortController initSireneOfShameController(
			final SireneOfShameJavaFXApplication gui) {

		final SireneOfShameHostSerialPortController sireneOfShameController = new SireneOfShameHostSerialPortController();

		final SireneOfShameHostControllerEventsListener eventsListener = new SireneOfShameHostControllerEventsListener() {
			@Override
			public void onStateChanged(final ESireneOfShameAlarmLevel from, final ESireneOfShameAlarmLevel to) {
				gui.onAlarmLevelChanged(to);
			}
		};
		sireneOfShameController.subscribe(eventsListener);

		return sireneOfShameController;
	}

	private static JenkinsApiJsonRessourceScanner initJenkinsApiJsonRessourceScanner(
			final SireneOfShameJavaFXController sireneOfShameJavaFXController) {

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

				final ESireneOfShameAlarmLevel highestState;
				if (jobsStatesSummary.contains("red")) {
					highestState = ESireneOfShameAlarmLevel.RED;
				} else if (jobsStatesSummary.contains("red_anime")) {
					highestState = ESireneOfShameAlarmLevel.RED_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("yellow")) {
					highestState = ESireneOfShameAlarmLevel.YELLOW;
				} else if (jobsStatesSummary.contains("yellow_anime")) {
					highestState = ESireneOfShameAlarmLevel.YELLOW_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("blue_anime")) {
					highestState = ESireneOfShameAlarmLevel.GREENBLUE_EXPECTING_UPDATE;
				} else if (jobsStatesSummary.contains("blue")) {
					highestState = ESireneOfShameAlarmLevel.GREENBLUE;
				} else {
					highestState = ESireneOfShameAlarmLevel.UNDEFINED;
				}
				sireneOfShameJavaFXController.setAlarmLevelTo(highestState);
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

		sireneOfShameController.connect();

		final String portName = sireneOfShameController.getPortName();
		final ESireneOfShameAlarmLevel alarmLevel = sireneOfShameController.getCurrentAlarmLevel();

		gui.onConnected(portName, alarmLevel);

		jenkinsApiJsonScannerThread = new Thread(jenkinsApiJsonScanner);
		jenkinsApiJsonScannerThread.start();
	}

	public void disconnect() {

		jenkinsApiJsonScannerThread.interrupt();
		sireneOfShameController.disconnect();

		gui.onDisconnected();
	}

	public void setAlarmLevelTo(final ESireneOfShameAlarmLevel newAlarmLevel) {

		final ESireneOfShameAlarmLevel currentState = sireneOfShameController.getCurrentAlarmLevel();
		if (currentState != newAlarmLevel) {

			sireneOfShameController.setAlarmLevelTo(newAlarmLevel);
			gui.onAlarmLevelChanged(newAlarmLevel);
		}
	}

}
