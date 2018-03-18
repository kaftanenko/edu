package app.sirenofshame.common.host.service.jenkins.client.http.scanner;

import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import app.sirenofshame.common.host.service.jenkins.client.util.JenkinsClientLogManager;

public class DefaultJenkinsApiJsonResourceScannerEventsListener implements JenkinsApiJsonResourceScannerEventsListener {

  // ... dependencies

  private static final Logger LOG = JenkinsClientLogManager.getLogger();

  // ... events handler methods

  @Override
  public void onBeforeResourceRequest(final String resourcePath) {

    final String currentTime = getCurrentTimeInGMTFormat();
    LOG.info("Sent Request:\n" //
        + "{\n" //
        + "\t currentTime: " + currentTime + ", \n" //
        + "\t resourcePath: " + resourcePath + " \n" //
        + "}");
  }

  @Override
  public void onAfterResourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

    final String currentTime = getCurrentTimeInGMTFormat();
    LOG.info("Received Response:\n" //
        + "{\n" //
        + "\t currentTime: " + currentTime + ", \n" //
        + "\t resourcePath: " + resourcePath + ", \n" //
        + "\t responseBody: " + jsonRootNode + "\n" //
        + "}");
  }

  // ... helper methods

  private String getCurrentTimeInGMTFormat() {

    final String currentTime = Calendar.getInstance().getTime().toGMTString();
    return currentTime;
  }

}
