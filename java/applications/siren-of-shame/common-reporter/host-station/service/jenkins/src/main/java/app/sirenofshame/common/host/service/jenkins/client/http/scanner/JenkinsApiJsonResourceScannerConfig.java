package app.sirenofshame.common.host.service.jenkins.client.http.scanner;

import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;

public class JenkinsApiJsonResourceScannerConfig {

	// ... properties

	private final JenkinsHttpClientConfig jenkinsHttpClientConfig;
	private final long pollingTaktDurationInMs;
	private final String resourcePath;

	// ... constructors

	private JenkinsApiJsonResourceScannerConfig(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingTaktDurationInMs, final String resourcePath) {

		this.jenkinsHttpClientConfig = jenkinsHttpClientConfig;
		this.pollingTaktDurationInMs = pollingTaktDurationInMs;
		this.resourcePath = resourcePath;
	}

	public static JenkinsApiJsonResourceScannerConfig of(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingTaktDurationInMs, final String resourcePath) {

		return new JenkinsApiJsonResourceScannerConfig(jenkinsHttpClientConfig, pollingTaktDurationInMs, resourcePath);
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
