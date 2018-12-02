package app.sirenofshame.multistate.host.service.jenkins.client.http.scanner;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import app.sirenofshame.common.host.service.jenkins.client.http.parser.EJenkinsApiNodeType;
import app.sirenofshame.common.host.service.jenkins.client.http.parser.JenkinsApiJsonParser;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class JenkinsApiJsonResourceTraversingScanner extends JenkinsApiJsonResourceScanner {

  // ... constructors

  public JenkinsApiJsonResourceTraversingScanner(final JenkinsApiJsonResourceScannerConfig config) {

    super(config);
  }

  // ... business methods

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

  // ... helper methods

  private String getResourcePath(final Map<String, Object> node) {

    final String nodeUrl = JenkinsApiJsonParser.getNodeUrl(node);
    final String hostUrl = config.getJenkinsHttpClientConfig().getHostURL();

    final String resourcePath = StringUtils.substringAfter(nodeUrl, hostUrl);
    return resourcePath;
  }

}
