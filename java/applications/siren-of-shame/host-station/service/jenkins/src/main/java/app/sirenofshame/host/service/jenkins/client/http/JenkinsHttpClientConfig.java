package app.sirenofshame.host.service.jenkins.client.http;

import java.util.ArrayList;
import java.util.List;

public class JenkinsHttpClientConfig {

	// ... properties

	private String hostUrl;
	private String username;
	private String password;

	private List<JenkinsJobsScanPlan> jobsToScan;

	// ... constructors

	private JenkinsHttpClientConfig(final String hostUrl, final String username, final String password) {

		this.hostUrl = hostUrl;
		this.username = username;
		this.password = password;

		this.jobsToScan = new ArrayList<>();
	}

	public static JenkinsHttpClientConfig of(final String hostUrl, final String username, final String password) {

		return new JenkinsHttpClientConfig(hostUrl, username, password);
	}

	// ... getter methods

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(final String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<JenkinsJobsScanPlan> getJobsToScan() {
		return jobsToScan;
	}

	public void setJobsToScan(final List<JenkinsJobsScanPlan> jobsToScan) {
		this.jobsToScan = jobsToScan;
	}

}
