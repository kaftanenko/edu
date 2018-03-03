package app.sirenofshame.common.host.service.jenkins.client.http.parser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JenkinsApiJsonParser {

  // ... constants

  private static final String JENKINS_RESPONSE__JSON_NODE_NAME__JOBS = "jobs";

  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__CLASS = "_class";
  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR = "color";
  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__NAME = "name";
  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__URL = "url";

  // ... business methods

  public static Set<String> collectJobsStates(final Collection<Map<String, Object>> jobsNode,
      final Pattern jobNamesRegEx) {

    return jobsNode //
        .stream() //
        .filter(e -> {

          final Object jobName = e.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__NAME);

          if (jobName != null && jobNamesRegEx.matcher(jobName.toString()).matches()) {
            return true;
          } else {
            return false;
          }
        }) //
        .filter(e -> e.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR) != null) //
        .map(e -> e.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR).toString()) //
        .collect(Collectors.toSet()) //
    ;
  }

  @SuppressWarnings("unchecked")
  public static Collection<Map<String, Object>> extractJobNodes(final Map<String, Object> rootNode) {

    return (Collection<Map<String, Object>>) rootNode.get(JENKINS_RESPONSE__JSON_NODE_NAME__JOBS);
  }

  public static EJenkinsApiNodeType getNodeType(final Map<String, Object> node) {

    final String nodeClass = (String) node.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__CLASS);

    if (nodeClass.endsWith(".Folder")) {

      return EJenkinsApiNodeType.FOLDER;
    } else if (nodeClass.endsWith(".WorkflowJob")) {

      return EJenkinsApiNodeType.WORKFLOW_JOB;
    } else {
      return EJenkinsApiNodeType.UNDEFINED;
    }
  }

  public static String getNodeUrl(final Map<String, Object> node) {

    final String nodeUrl = (String) node.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__URL);
    return nodeUrl;
  }

  public static void replaceNodeContent(final Map<String, Object> node, final Map<String, Object> nodeNewContent) {

    node.clear();
    node.putAll(nodeNewContent);
  }

}
