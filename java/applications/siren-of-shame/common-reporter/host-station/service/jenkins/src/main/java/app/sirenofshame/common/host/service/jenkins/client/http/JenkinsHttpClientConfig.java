package app.sirenofshame.common.host.service.jenkins.client.http;

public class JenkinsHttpClientConfig {

	// ... properties

	private String hostUrl;
	private String username;
	private String password;

	// ... constructors

	private JenkinsHttpClientConfig(final String hostUrl, final String username, final String password) {

		this.hostUrl = hostUrl;
		this.username = username;
		this.password = password;
	}

	public static JenkinsHttpClientConfig of(final String hostUrl, final String username, final String password) {

		return new JenkinsHttpClientConfig(hostUrl, username, password);
	}

	// ... getter/setter methods

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

}
