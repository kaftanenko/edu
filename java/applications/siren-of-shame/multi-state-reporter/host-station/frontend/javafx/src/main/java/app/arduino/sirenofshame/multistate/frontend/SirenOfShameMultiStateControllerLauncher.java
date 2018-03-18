package app.arduino.sirenofshame.multistate.frontend;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.arduino.sirenofshame.multistate.service.host.SirenOfShameMultiStateSerialChannelHostController;
import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerEventsListener;
import app.sirenofshame.multistate.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceTraversingScanner;

public class SirenOfShameMultiStateControllerLauncher {

  // ... dependencies

  public static void main(final String[] args) {

    final String hostUrl = JenkinsHttpClientConstants.HOST_URL;
    final String username = JenkinsHttpClientConstants.AUTH_USERNAME;
    final String password = JenkinsHttpClientConstants.AUTH_PASSWORD;

    final JenkinsHttpClientConfig jenkinsHttpClientConfig = JenkinsHttpClientConfig.of(hostUrl, username, password);

    final long pollingTaktDurationInMs = 10000; // ... 10 sec.
    final String resourcePath = "/";

    final JenkinsApiJsonResourceScannerConfig config = JenkinsApiJsonResourceScannerConfig.of(jenkinsHttpClientConfig,
        pollingTaktDurationInMs, resourcePath);

    final SirenOfShameMultiStateSerialChannelHostController serialChannelController = new SirenOfShameMultiStateSerialChannelHostController();
    serialChannelController.connect();

    final JenkinsApiJsonResourceScannerEventsListener eventsListener = new JenkinsApiJsonResourceScannerEventsListener() {

      @Override
      public void onBeforeResourceRequest(final String resourcePath) {
        // ... not interesting in.
      }

      @Override
      public void onAfterResourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

        try {
          final String message = new ObjectMapper().writeValueAsString(jsonRootNode);
          serialChannelController.uploadResource(message);
        } catch (final JsonProcessingException ex) {
          throw new RuntimeException(ex);
        }
      }
    };

    final JenkinsApiJsonResourceScanner jenkinsScanner = new JenkinsApiJsonResourceTraversingScanner(config);
    jenkinsScanner.subscribe(eventsListener);
    jenkinsScanner.run();
  }

}
