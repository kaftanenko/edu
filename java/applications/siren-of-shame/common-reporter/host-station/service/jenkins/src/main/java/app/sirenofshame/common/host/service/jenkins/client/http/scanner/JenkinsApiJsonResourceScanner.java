package app.sirenofshame.common.host.service.jenkins.client.http.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClient;
import app.sirenofshame.common.host.service.jenkins.client.util.JenkinsClientLogManager;

public class JenkinsApiJsonResourceScanner implements Runnable {

	// ... dependencies

	private static final Logger LOG = JenkinsClientLogManager.getLogger();

	// ... properties

	private final JenkinsHttpClient jenkinsHttpClient;

	private final JenkinsApiJsonResourceScannerConfig config;
	private final List<JenkinsApiJsonResourceScannerEventsListener> eventsListeners;

	// ... constructors

	public JenkinsApiJsonResourceScanner(final JenkinsApiJsonResourceScannerConfig config) {

		this.config = config;
		this.eventsListeners = new ArrayList<>();
		this.jenkinsHttpClient = new JenkinsHttpClient(config.getJenkinsHttpClientConfig());

		subscribe(new DefaultJenkinsApiJsonResourceScannerEventsListener());
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
				LOG.error("... polling thread interrupted.", ex);
			}

			try {

				notifyEventsListenersAboutresourceRequest(resourcePath);

				final Map<String, Object> jsonRootNode = jenkinsHttpClient.callJsonApi(resourcePath);

				notifyEventsListenersAboutResourceResponse(resourcePath, jsonRootNode);
			} catch (final Exception ex) {
				LOG.error(ex);
			}
		}
	}

	// ... events management methods

	protected void notifyEventsListenersAboutresourceRequest(final String resourcePath) {

		for (final JenkinsApiJsonResourceScannerEventsListener eventsListener : eventsListeners) {

			eventsListener.onBeforeResourceRequest(resourcePath);
		}
	}

	protected void notifyEventsListenersAboutResourceResponse(final String resourcePath,
			final Map<String, Object> jsonRootNode) {

		for (final JenkinsApiJsonResourceScannerEventsListener eventsListener : eventsListeners) {

			eventsListener.onAfterResourceResponse(resourcePath, jsonRootNode);
		}
	}

	public void subscribe(final JenkinsApiJsonResourceScannerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	public void unsubscribe(final JenkinsApiJsonResourceScannerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

};
