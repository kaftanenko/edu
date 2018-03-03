package app.arduino.sirenofshame.frontend.javafx.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JenkinsApiJsonParser {

  // ... constants

  private static final String JENKINS_RESPONSE__JSON_NODE_NAME__JOBS = "jobs";
  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__NAME = "name";
  private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR = "color";

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
  public static Collection<Map<String, Object>> extractJobsNode(final Map<String, Object> rootNode) {

    return (Collection<Map<String, Object>>) rootNode.get(JENKINS_RESPONSE__JSON_NODE_NAME__JOBS);
  }

}
