package app.arduino.sereneofshame.frontend.impl.javafx;

import static app.sereneofshame.host.service.jenkins.JenkinsConstants.AUTH_PASSWORD;
import static app.sereneofshame.host.service.jenkins.JenkinsConstants.AUTH_USERNAME;
import static app.sereneofshame.host.service.jenkins.JenkinsConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameControllerEventsListener;
import app.arduino.sereneofshame.service.impl.serial.host.SireneOfShameSerialHostController;
import app.sereneofshame.host.service.jenkins.client.JenkinsHttpClientConfig;
import app.sereneofshame.host.service.jenkins.parser.JenkinsJsonApiParser;
import app.sereneofshame.host.service.jenkins.scanner.JenkinsApiJsonRessourceScanner;
import app.sereneofshame.host.service.jenkins.scanner.JenkinsApiJsonRessourceScannerConfig;
import app.sereneofshame.host.service.jenkins.scanner.JenkinsApiJsonRessourceScannerEventsListener;

public class SireneOfShameJavaFXController implements AutoCloseable {

	// ... constants

	private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs";
	protected static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

	// ... properties

	private final SireneOfShameJavaFXApplication gui;
	private final SireneOfShameSerialHostController sireneOfShameController;

	private final JenkinsApiJsonRessourceScanner jenkinsApiJsonScanner;
	private Thread jenkinsApiJsonScannerThread;

	// ... constructors

	public SireneOfShameJavaFXController(final SireneOfShameJavaFXApplication gui) {

		this.gui = gui;

		this.sireneOfShameController = initSireneOfShameController(gui);

		jenkinsApiJsonScanner = initJenkinsApiJsonRessourceScanner(this);
	}

	private static SireneOfShameSerialHostController initSireneOfShameController(
			final SireneOfShameJavaFXApplication gui) {

		final SireneOfShameSerialHostController sireneOfShameController = new SireneOfShameSerialHostController();

		final SireneOfShameControllerEventsListener eventsListener = new SireneOfShameControllerEventsListener() {
			@Override
			public void onStateChanged(final ESireneOfShameState from, final ESireneOfShameState to) {
				gui.onStateChanged(to);
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
			public void onRessourceRead(final Map<String, Object> jsonRootNode) {

				final Collection<Map<String, Object>> jsonJobsNode = JenkinsJsonApiParser.extractJobsNode(jsonRootNode);
				final Set<String> jobsStatesSummary = JenkinsJsonApiParser.collectJobsStates(jsonJobsNode);

				final ESireneOfShameState highestState;
				if (jobsStatesSummary.contains("red") || jobsStatesSummary.contains("red_anime")) {
					highestState = ESireneOfShameState.RED;
				} else if (jobsStatesSummary.contains("yellow") || jobsStatesSummary.contains("yellow_anime")) {
					highestState = ESireneOfShameState.YELLOW;
				} else {
					highestState = ESireneOfShameState.GREENBLUE;
				}
				sireneOfShameJavaFXController.setState(highestState);
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
		final ESireneOfShameState state = sireneOfShameController.getState();

		gui.onConnected(portName, state);

		jenkinsApiJsonScannerThread = new Thread(jenkinsApiJsonScanner);
		jenkinsApiJsonScannerThread.start();
	}

	public void disconnect() {

		jenkinsApiJsonScannerThread.interrupt();
		sireneOfShameController.disconnect();

		gui.onDisconnected();
	}

	public void setState(final ESireneOfShameState newState) {

		final ESireneOfShameState currentState = sireneOfShameController.getState();
		if (currentState != newState) {

			sireneOfShameController.setState(newState);
			gui.onStateChanged(newState);
		}
	}

}
