package app.sirenofshame.host.service.jenkins.client.json.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import app.sirenofshame.host.service.jenkins.client.JenkinsClientFactory;
import app.sirenofshame.host.service.jenkins.client.http.JenkinsHttpClient;

public class JenkinsApiJsonRessourceScanner implements Runnable {

	// ... dependencies

	private static final Logger LOG = JenkinsClientFactory.getLogger();

	// ... properties

	private final JenkinsHttpClient jenkinsHttpClient;

	private final JenkinsApiJsonRessourceScannerConfig config;
	private final List<JenkinsApiJsonRessourceScannerEventsListener> eventsListeners;

	// ... constructors

	public JenkinsApiJsonRessourceScanner(final JenkinsApiJsonRessourceScannerConfig config) {

		this.config = config;
		this.eventsListeners = new ArrayList<>();
		this.jenkinsHttpClient = new JenkinsHttpClient(config.getJenkinsHttpClientConfig());

		subscribe(new DefaultJenkinsApiJsonRessourceScannerEventsListener());
	}

	// ... business methods

	@Override
	public void run() {

		final String resourcePath = config.getResourcePath();
		final long pollingTaktDurationInMs = config.getPollingTaktDurationInMs();

		while (true) {

			try {
				Thread.sleep(pollingTaktDurationInMs);
			} catch (final InterruptedException ex) {
				LOG.error("... polling thread interrupted.");
			}

			notifyEventsListenersAboutRessourceRequest(resourcePath);

			final Map<String, Object> jsonRootNode = jenkinsHttpClient.callJsonApi(resourcePath);

			notifyEventsListenersAboutResourceResponse(resourcePath, jsonRootNode);
		}
	}

	// ... events management methods

	protected void notifyEventsListenersAboutRessourceRequest(final String resourcePath) {

		for (final JenkinsApiJsonRessourceScannerEventsListener eventsListener : eventsListeners) {

			eventsListener.onBeforeRessourceRequest(resourcePath);
		}
	}

	protected void notifyEventsListenersAboutResourceResponse(final String resourcePath,
			final Map<String, Object> jsonRootNode) {

		for (final JenkinsApiJsonRessourceScannerEventsListener eventsListener : eventsListeners) {

			eventsListener.onAfterRessourceResponse(resourcePath, jsonRootNode);
		}
	}

	public void subscribe(final JenkinsApiJsonRessourceScannerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	public void unsubscribe(final JenkinsApiJsonRessourceScannerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

};
