package app.arduino.sirenofshame.multistate.frontend;

import java.util.regex.Pattern;

public class JenkinsHttpClientConstants {

  // ... constants

  public static final String HOST_URL = "http://jenkins:80";

  public static final String AUTH_USERNAME = "username";
  public static final String AUTH_PASSWORD = "password";

  public static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs/job/inte-b/";
  public static final Pattern JENKINS_JOB_NAMES_TO_MONITOR__REGEX = Pattern.compile(".+integration");
  public static final long JENKINS_POLLING_PERIOD__IN_SEC__15 = 15;

}
