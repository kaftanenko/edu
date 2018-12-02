package app.arduino.sirenofshame.multistate.frontend;

import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.AUTH_PASSWORD;
import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.AUTH_USERNAME;
import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.HOST_URL;
import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.JENKINS_JOB_NAMES_TO_MONITOR__REGEX;
import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.JENKINS_POLLING_PERIOD__IN_SEC__15;
import static app.arduino.sirenofshame.multistate.frontend.JenkinsHttpClientConstants.JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.arduino.sirenofshame.multistate.service.host.SirenOfShameMultiStateSerialChannelHostController;
import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerEventsListener;
import app.sirenofshame.multistate.host.service.jenkins.client.http.scanner.mock.JenkinsApiJsonResourceTraversingScannerMock;

public class SirenOfShameMultiStateControllerLauncher {

  // ... dependencies

  private static final Logger LOG = LogManager.getLogger(SirenOfShameMultiStateControllerLauncher.class);

  // ... launch methods

  public static void main(final String[] args) {

    final JenkinsApiJsonResourceScannerConfig config = JenkinsApiJsonResourceScannerConfig.of( //

        JenkinsHttpClientConfig.of( //
            HOST_URL, //
            AUTH_USERNAME, //
            AUTH_PASSWORD //
        ), //
        JENKINS_POLLING_PERIOD__IN_SEC__15, //
        JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS, //
        JENKINS_JOB_NAMES_TO_MONITOR__REGEX //
    );

    final SirenOfShameMultiStateSerialChannelHostController serialChannelController = new SirenOfShameMultiStateSerialChannelHostController();
    serialChannelController.connect("COM9");

    final JenkinsApiJsonResourceScannerEventsListener eventsListener = new JenkinsApiJsonResourceScannerEventsListener() {

      @Override
      public void onBeforeResourceRequest(final String resourcePath) {
        // ... not interesting in.
      }

      @Override
      public void onAfterResourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

        try {

          final String data = new ObjectMapper().writeValueAsString(jsonRootNode);
          serialChannelController.uploadResource(resourcePath + "jenkins.jsn", data);
        } catch (final JsonProcessingException ex) {
          LOG.error(ex);
        }
      }
    };

    final JenkinsApiJsonResourceScanner jenkinsScanner = new JenkinsApiJsonResourceTraversingScannerMock(config);
    jenkinsScanner.subscribe(eventsListener);
    jenkinsScanner.run();
  }

}
