package app.sirenofshame.common.host.service.jenkins.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JenkinsClientLogManager {

  // ... dependencies

  private static final Logger JENKINS_CLIENT_LOG = LogManager.getLogger("JENKINS_CLIENT");

  // ... business methods

  public static Logger getLogger() {

    return JENKINS_CLIENT_LOG;
  }
}
