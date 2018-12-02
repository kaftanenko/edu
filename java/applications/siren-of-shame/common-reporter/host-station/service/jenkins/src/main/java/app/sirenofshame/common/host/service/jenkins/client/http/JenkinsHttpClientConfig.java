package app.sirenofshame.common.host.service.jenkins.client.http;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JenkinsHttpClientConfig {

  // ... properties

  private String hostURL;
  private String username;
  private String password;

  // ... constructors

  public JenkinsHttpClientConfig() {

    super();
  }

  private JenkinsHttpClientConfig(final String hostUrl, final String username, final String password) {

    this.hostURL = hostUrl;
    this.username = username;
    this.password = password;
  }

  public static JenkinsHttpClientConfig of(final String hostUrl, final String username, final String password) {

    return new JenkinsHttpClientConfig(hostUrl, username, password);
  }

  // ... getter/setter methods

  public String getHostURL() {
    return hostURL;
  }

  public void setHostURL(final String hostUrl) {
    this.hostURL = hostUrl;
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

  // ... helper methods

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
