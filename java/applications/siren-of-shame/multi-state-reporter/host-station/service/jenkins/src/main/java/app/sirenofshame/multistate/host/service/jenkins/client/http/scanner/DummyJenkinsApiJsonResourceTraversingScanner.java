package app.sirenofshame.multistate.host.service.jenkins.client.http.scanner;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class DummyJenkinsApiJsonResourceTraversingScanner extends JenkinsApiJsonResourceScanner {

  // ... constructors

  public DummyJenkinsApiJsonResourceTraversingScanner(final JenkinsApiJsonResourceScannerConfig config) {

    super(config);
  }

  @Override
  protected Map<String, Object> callJsonApi(final String resourcePath) {

    try {
      final File file = new File(this.getClass().getResource("jenkins-info-mock-data.json").toURI());
      final String bodyAsString = FileUtils.readFileToString(file);

      final Map<String, Object> jsonContentAsMap = //
          new ObjectMapper().readValue(bodyAsString, new TypeReference<Map<String, Object>>() {
          });
      return jsonContentAsMap;
    } catch (final Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}
