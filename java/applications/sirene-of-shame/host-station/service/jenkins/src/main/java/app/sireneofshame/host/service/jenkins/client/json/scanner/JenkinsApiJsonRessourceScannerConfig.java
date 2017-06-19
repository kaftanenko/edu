package app.sireneofshame.host.service.jenkins.client.json.scanner;

import app.sireneofshame.host.service.jenkins.client.http.JenkinsHttpClientConfig;

public class JenkinsApiJsonRessourceScannerConfig {

	// ... properties

	private final JenkinsHttpClientConfig jenkinsHttpClientConfig;
	private final long pollingTaktDurationInMs;
	private final String resourcePath;

	// ... constructors

	private JenkinsApiJsonRessourceScannerConfig(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingTaktDurationInMs, final String resourcePath) {

		this.jenkinsHttpClientConfig = jenkinsHttpClientConfig;
		this.pollingTaktDurationInMs = pollingTaktDurationInMs;
		this.resourcePath = resourcePath;
	}

	public static JenkinsApiJsonRessourceScannerConfig of(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingTaktDurationInMs, final String resourcePath) {

		return new JenkinsApiJsonRessourceScannerConfig(jenkinsHttpClientConfig, pollingTaktDurationInMs, resourcePath);
	}

	// ... getter methods

	public JenkinsHttpClientConfig getJenkinsHttpClientConfig() {
		return jenkinsHttpClientConfig;
	}

	public long getPollingTaktDurationInMs() {
		return pollingTaktDurationInMs;
	}

	public String getResourcePath() {
		return resourcePath;
	}

}
