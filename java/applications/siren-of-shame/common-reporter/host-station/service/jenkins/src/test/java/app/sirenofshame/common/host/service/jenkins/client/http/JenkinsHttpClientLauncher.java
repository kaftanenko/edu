package app.sirenofshame.common.host.service.jenkins.client.http;

import java.util.Map;

public class JenkinsHttpClientLauncher {

  // ... constants

  public static final String HOST_URL = "http://jenkins:80";

  public static final String AUTH_USERNAME = "username";
  public static final String AUTH_PASSWORD = "password";

  private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/";

  // ... launch methods

  public static void main(final String... args) {

    final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of(HOST_URL, AUTH_USERNAME, AUTH_PASSWORD);
    final JenkinsHttpClient controller = new JenkinsHttpClient(config);

    final Map<String, Object> jsonRootNode = controller.callJsonApi(JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS);
    System.out.println(jsonRootNode);
  }

}
