package app.sirenofshame.multistate.host.service.jenkins.client.http.scanner;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.sirenofshame.common.host.service.jenkins.client.http.parser.EJenkinsApiNodeType;
import app.sirenofshame.common.host.service.jenkins.client.http.parser.JenkinsApiJsonParser;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class JenkinsApiJsonResourceTraversingScanner extends JenkinsApiJsonResourceScanner {

  // ... constructors

  public JenkinsApiJsonResourceTraversingScanner(final JenkinsApiJsonResourceScannerConfig config) {

    super(config);
  }

  @Override
  protected Map<String, Object> callJsonApi(final String resourcePath) {

    final Map<String, Object> resource = super.callJsonApi(resourcePath);

    final Collection<Map<String, Object>> jobNodes = JenkinsApiJsonParser.extractJobNodes(resource);
    for (final Map<String, Object> node : jobNodes) {

      final EJenkinsApiNodeType nodeType = JenkinsApiJsonParser.getNodeType(node);
      switch (nodeType) {

        case FOLDER:

          final String subfolderResourcePath = getResourcePath(node);
          final Map<String, Object> subfolderDetails = callJsonApi(subfolderResourcePath);

          JenkinsApiJsonParser.replaceNodeContent(node, subfolderDetails);
          break;
        default: // ... nothing to do, no further node types must be
                 // handled here.
      }
    }
    return resource;
  }

  protected Map<String, Object> callJsonApiWithMockData() {

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

  private String getResourcePath(final Map<String, Object> node) {

    final String nodeUrl = JenkinsApiJsonParser.getNodeUrl(node);
    final String hostUrl = config.getJenkinsHttpClientConfig().getHostUrl();

    final String resourcePath = StringUtils.substringAfter(nodeUrl, hostUrl);
    return resourcePath;
  }

}
