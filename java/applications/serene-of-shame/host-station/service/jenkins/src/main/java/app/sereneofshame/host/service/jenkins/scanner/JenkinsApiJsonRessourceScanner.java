package app.sereneofshame.host.service.jenkins.scanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import app.sereneofshame.host.service.jenkins.client.JenkinsHttpClient;

public class JenkinsApiJsonRessourceScanner implements Runnable {

	// ... properties

	private final JenkinsHttpClient jenkinsHttpClient;

	private final JenkinsApiJsonRessourceScannerConfig config;
	private final List<JenkinsApiJsonRessourceScannerEventsListener> eventsListeners;

	// ... constructors

	public JenkinsApiJsonRessourceScanner(final JenkinsApiJsonRessourceScannerConfig config) {

		this.config = config;
		this.eventsListeners = new ArrayList<>();
		this.jenkinsHttpClient = new JenkinsHttpClient(config.getJenkinsHttpClientConfig());
	}

	// ... business methods

	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(config.getPollingPeriodInMs());
			} catch (final InterruptedException ex) {
				System.out.println(JenkinsApiJsonRessourceScanner.class.getSimpleName() + " was interrupted.");
			}

			final String currentTime = Calendar.getInstance().toString();
			System.out.println(currentTime + ": Request Jenkins Ressource: " + config.getResourcePath());

			final Map<String, Object> jsonRootNode = jenkinsHttpClient.callJsonApi(config.getResourcePath());
			notifyEventsListenersAboutResourceRead(jsonRootNode);
		}
	}

	// ... events management methods

	protected void notifyEventsListenersAboutResourceRead(final Map<String, Object> jsonRootNode) {

		for (final JenkinsApiJsonRessourceScannerEventsListener eventsListener : eventsListeners) {

			eventsListener.onRessourceRead(jsonRootNode);
		}
	}

	public void subscribe(final JenkinsApiJsonRessourceScannerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	public void unsubscribe(final JenkinsApiJsonRessourceScannerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

};
