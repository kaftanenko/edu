package app.sereneofshame.host.service.jenkins;

public class JenkinsHttpClientConfig {

	// ... properties

	private final String hostUrl;
	private final String username;
	private final String password;

	// ... constructors

	private JenkinsHttpClientConfig(final String hostUrl, final String username, final String password) {

		this.hostUrl = hostUrl;
		this.username = username;
		this.password = password;
	}

	public static JenkinsHttpClientConfig of(final String hostUrl, final String username, final String password) {

		return new JenkinsHttpClientConfig(hostUrl, username, password);
	}

	// ... getter methods

	public String getHostUrl() {
		return hostUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
