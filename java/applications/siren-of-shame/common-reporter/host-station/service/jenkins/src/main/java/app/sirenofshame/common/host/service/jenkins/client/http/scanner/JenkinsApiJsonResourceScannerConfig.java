package app.sirenofshame.common.host.service.jenkins.client.http.scanner;

import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;

public class JenkinsApiJsonResourceScannerConfig {

  // ... properties

  private JenkinsHttpClientConfig jenkinsHttpClientConfig;

  private long pollingPeriodInSec;
  private String jobsFolderPath;
  private Pattern jobsNameRegExPattern;

  // ... constructors

  public JenkinsApiJsonResourceScannerConfig() {

    super();
  }

  private JenkinsApiJsonResourceScannerConfig( //
      final JenkinsHttpClientConfig jenkinsHttpClientConfig, //
      final long pollingTaktDurationInMs, //
      final String jobsFolderPath, //
      final Pattern jobsNameRegExPattern //
  ) {

    this.jenkinsHttpClientConfig = jenkinsHttpClientConfig;
    this.pollingPeriodInSec = pollingTaktDurationInMs;
    this.jobsFolderPath = jobsFolderPath;
    this.jobsNameRegExPattern = jobsNameRegExPattern;
  }

  public static JenkinsApiJsonResourceScannerConfig of(//
      final JenkinsHttpClientConfig jenkinsHttpClientConfig, //
      final long pollingTaktDurationInMs, //
      final String jobsFolderPath, //
      final Pattern jobsNameRegExPattern //
  ) {

    return new JenkinsApiJsonResourceScannerConfig( //
        jenkinsHttpClientConfig, //
        pollingTaktDurationInMs, //
        jobsFolderPath, //
        jobsNameRegExPattern //
    );
  }

  // ... getter/setter methods

  public JenkinsHttpClientConfig getJenkinsHttpClientConfig() {
    return jenkinsHttpClientConfig;
  }

  public void setJenkinsHttpClientConfig(JenkinsHttpClientConfig jenkinsHttpClientConfig) {
    this.jenkinsHttpClientConfig = jenkinsHttpClientConfig;
  }

  public long getPollingPeriodInSec() {
    return pollingPeriodInSec;
  }

  public void setPollingPeriodInSec(long pollingPeriodInSec) {
    this.pollingPeriodInSec = pollingPeriodInSec;
  }

  public String getJobsFolderPath() {
    return jobsFolderPath;
  }

  public void setJobsFolderPath(String resourcePath) {
    this.jobsFolderPath = resourcePath;
  }

  public Pattern getJobsNameRegExPattern() {
    return jobsNameRegExPattern;
  }

  public void setJobsNameRegExPattern(Pattern jobsNameRegExPattern) {
    this.jobsNameRegExPattern = jobsNameRegExPattern;
  }

  // ... helper methods

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
