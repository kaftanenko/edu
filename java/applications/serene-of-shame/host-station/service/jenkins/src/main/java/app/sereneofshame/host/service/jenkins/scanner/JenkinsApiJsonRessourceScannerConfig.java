package app.sereneofshame.host.service.jenkins.scanner;

import app.sereneofshame.host.service.jenkins.client.JenkinsHttpClientConfig;

public class JenkinsApiJsonRessourceScannerConfig {

	// ... properties

	private final JenkinsHttpClientConfig jenkinsHttpClientConfig;
	private final long pollingPeriodInMs;
	private final String resourcePath;

	// ... constructors

	private JenkinsApiJsonRessourceScannerConfig(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingPeriodInMs, final String resourcePath) {

		this.jenkinsHttpClientConfig = jenkinsHttpClientConfig;
		this.pollingPeriodInMs = pollingPeriodInMs;
		this.resourcePath = resourcePath;
	}

	public static JenkinsApiJsonRessourceScannerConfig of(final JenkinsHttpClientConfig jenkinsHttpClientConfig,
			final long pollingPeriodInMs, final String resourcePath) {

		return new JenkinsApiJsonRessourceScannerConfig(jenkinsHttpClientConfig, pollingPeriodInMs, resourcePath);
	}

	// ... getter methods

	public JenkinsHttpClientConfig getJenkinsHttpClientConfig() {
		return jenkinsHttpClientConfig;
	}

	public long getPollingPeriodInMs() {
		return pollingPeriodInMs;
	}

	public String getResourcePath() {
		return resourcePath;
	}

}
