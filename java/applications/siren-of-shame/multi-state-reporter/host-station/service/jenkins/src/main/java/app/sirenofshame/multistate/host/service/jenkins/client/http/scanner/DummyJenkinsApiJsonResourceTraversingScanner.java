package app.sirenofshame.multistate.host.service.jenkins.client.http.scanner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class DummyJenkinsApiJsonResourceTraversingScanner //
    extends JenkinsApiJsonResourceScanner //
{

  // ... constructors

  public DummyJenkinsApiJsonResourceTraversingScanner(final JenkinsApiJsonResourceScannerConfig config) {

    super(config);
  }

  @Override
  protected Map<String, Object> callJsonApi(final String resourcePath) {

    try {

      final String bodyAsString = getMockData();
      final Map<String, Object> jsonContentAsMap = //
          new ObjectMapper().readValue(bodyAsString, new TypeReference<Map<String, Object>>() {
          });
      return jsonContentAsMap;
    } catch (final Exception ex) {
      LOG.error(ex);
      throw new RuntimeException(ex);
    }
  }

  // ... helper methods

  private String bodyAsString = null;

  private String getMockData() throws IOException {

    if (bodyAsString == null) {

      final File file = new File(
          "D:/projects/it/edu/projects/java/applications/siren-of-shame/multi-state-reporter/host-station/service/jenkins/src/main/resources/jenkins-info-mock-data.json");
      bodyAsString = FileUtils.readFileToString(file);
    }

    return bodyAsString;
  }

}
